package com.emerging.cloud.app.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

import io.seata.rm.datasource.DataSourceProxy;


@Configuration
public class DatabaseConfiguration {

	
	@Bean(destroyMethod = "close", initMethod = "init")
	@ConfigurationProperties(prefix="spring.datasource")
	public DruidDataSource druidDataSource() {

		return new DruidDataSource();
	}
	
	
	@Bean
	public DataSourceProxy dataSourceProxy(DruidDataSource druidDataSource) {
	
		return new DataSourceProxy(druidDataSource);
	}
	

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceProxy);    
        return factoryBean.getObject();
    }
}
