package com.mark.demo.mina.client;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.mark.demo.mina.client.entity.Result;

/*
 *@author(huangxiaoping)
 *@date 2013-10-11
 */
public class ClientHandler extends IoHandlerAdapter {
	private static final Logger logger=Logger.getLogger(ClientHandler.class);
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		Result result=(Result)message;
		System.out.println("==================="+result.getTotal());
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		
	}

}
