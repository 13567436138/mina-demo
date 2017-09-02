package com.mark.demo.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mark.demo.mina.server.MainServer;

/*
*hxp(huang.xp@topcheer.com)
*2017年9月2日
*
*/
public class DemoServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		MainServer.getInstanse().stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		MainServer.getInstanse().start();
	}

}
