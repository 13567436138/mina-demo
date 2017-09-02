package com.mark.demo.mina.server;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.mark.demo.mina.server.entity.Order;
import com.mark.demo.mina.server.entity.Result;

/*
 *@author(huangxiaoping)
 *@date 2013-9-17
 *mina服务器消息处理器
 */
public class MainServerHandler extends IoHandlerAdapter {
	private static final Logger logger=Logger.getLogger(MainServerHandler.class);
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		List<Order> orderList=(List<Order>)message;
		Result result=new Result();
		double total=0;
		for(int i=0;i<orderList.size();i++){
			Order order=orderList.get(i);
			total+=order.getPrice()*order.getNumber();
		}
		result.setTotal(total);
		session.write(result);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		
	}

}
