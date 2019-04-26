package com.emerging.framework.core.html;


import com.emerging.framework.core.controller.BaseController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * html页面工具
 * 
 * @author llz
 *
 */
public class HtmlUnitUtil {

	protected static Logger LOG = LogManager.getLogger(BaseController.class);
	/**
	 * 执行url对应的页面并返回页面内容
	 * 
	 * @param url
	 * @return
	 */
	public static String parsehtml(String url) {
		WebClient wc = new WebClient(BrowserVersion.CHROME);
		wc.setJavaScriptTimeout(500000);
		wc.getOptions().setUseInsecureSSL(true);// 接受任何主机连接 无论是否有有效证书
		wc.getOptions().setJavaScriptEnabled(true);// 设置支持javascript脚本
		wc.getOptions().setCssEnabled(false);// 禁用css支持
		wc.getOptions().setThrowExceptionOnScriptError(false);// js运行错误时不抛出异常
		wc.getOptions().setTimeout(100000);// 设置连接超时时间
		wc.getOptions().setDoNotTrackEnabled(true);
		wc.setAjaxController(new NicelyResynchronizingAjaxController());// 设置Ajax异步

		try {
			System.out.println(url);
			//URLDecoder.decode(s)
			LOG.info("****"+url);
			HtmlPage page = wc.getPage(url);
			wc.waitForBackgroundJavaScript(30 * 1000);
			String result = page.getBody().asText();
			wc.close();
			String retReult = result.replaceAll(" ", "");
			if(retReult.length()==33){
				retReult = retReult.substring(1);;
			}
			return retReult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		wc.close();
		return null;
	}

	public static void main(String[] args) {
		try {
			String url = "http://192.168.16.218:7080/FPCYencrypt/encrypt/";
			String fpdm = "123";
			String fphm = "456";
			String kjje = "123.456";
			String kprq = "20180203";
			String yzmSj = "2018-02-03 10:49:21";
			String nowtime = yzmSj;
			String ckcode = url + "ckcode.html?fpdm=" + fpdm + "&nowtime=" + nowtime;
			System.out.println(ckcode);
			System.out.println(parsehtml(ckcode));
			String ck = url + "ck.html?fpdm=" + fpdm + "&fphm=" + fphm + "&kjje=" + kjje + "&kprq=" + kprq + "&yzmSj="
					+ yzmSj + "&yzm=好";
			System.out.println(ck);
			System.out.println(parsehtml(ck));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
