package com.mark.demo.mina.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mark.demo.mina.client.MinaClient;

/*
*hxp(huang.xp@topcheer.com)
*
*/
public class DemoServletContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		MinaClient.getInstance().close();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		MinaClient.getInstance();
	}

}
