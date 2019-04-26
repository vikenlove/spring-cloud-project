package com.emerging.cloud.app.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import reactor.core.publisher.Mono;
@Component
public class SentinelGatewayFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain chain) {
		String resource = serverWebExchange.getRequest().getPath().toString();
		this.initFlowRules(resource);
		this.initDegradeRule(resource);
		Entry entry = null;
		
		try {
			ContextUtil.enter(resource);
			entry = SphU.entry(resource, EntryType.IN, 1, serverWebExchange.getRequest().getQueryParams().values());
			return chain.filter(serverWebExchange);
		} catch (BlockException e) {
			if (!BlockException.isBlockException(e)) {
				Tracer.trace(e);
			}
			e.printStackTrace();
		} finally {
			if (entry != null) {
				entry.exit();
			}
			ContextUtil.exit();
		}
		serverWebExchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
		return serverWebExchange.getResponse().setComplete();
	}

	private  void initFlowRules(final String resource){
		List<FlowRule> rules = new ArrayList<FlowRule>();
		// 限流
		FlowRule rule = new FlowRule();
		rule.setResource(resource);
		rule.setCount(5);
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rule.setLimitApp("default");
		rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP_RATE_LIMITER);
		rules.add(rule);
		FlowRuleManager.loadRules(rules);
	}
	

    private  void initDegradeRule(final String resource) {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(resource);
        // set threshold rt, 10 ms
        rule.setCount(4900);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }
	
	
}
