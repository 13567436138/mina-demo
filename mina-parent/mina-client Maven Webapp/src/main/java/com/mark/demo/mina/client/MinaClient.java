package com.mark.demo.mina.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.mark.demo.mina.client.codec.CsFixProtocolCodecFactory;
import com.mark.demo.mina.client.entity.Order;

/*
 *@author(huangxiaoping)
 *@date 2013-10-11
 */
public class MinaClient {
	private volatile static MinaClient instance;
	private static SocketConnector connector;
    private static ConnectFuture future;
    private static IoSession session;
	
    public static MinaClient getInstance(){
    	
    	if(instance==null){
    		synchronized (MinaClient.class) {
				if(instance==null){
					instance=new MinaClient();
					
				}
			}
    	}
    	if(connector==null||!connector.isActive()||!session.isConnected()){
    		instance.connect("127.0.0.1",8888);
    	}
    	return instance;
    }
	public  boolean connect(String ip,int port) {
        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(3000);
        DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
        filterChain.addLast("codec", new ProtocolCodecFilter(new CsFixProtocolCodecFactory(Charset.forName("UTF-8"))));
        LoggingFilter loggingFilter = new LoggingFilter();
        loggingFilter.setMessageReceivedLogLevel(LogLevel.INFO);
        loggingFilter.setMessageSentLogLevel(LogLevel.INFO);
        filterChain.addLast("loger", loggingFilter);
        connector.setHandler(new ClientHandler());
        future = connector.connect(new InetSocketAddress(ip,port));
        future.awaitUninterruptibly();
        session = future.getSession();
        return true;
    }
 
    public void setAttribute(Object key, Object value) {
        session.setAttribute(key, value);
    }
    
    public void sendDemoMsg(){
		List<Order> orderList=new ArrayList<Order>();
		Order order=new Order();
		order.setItem("apple");
		order.setNumber(10);
		order.setPrice(5.0);
		Order order2=new Order();
		order2.setItem("apple");
		order2.setNumber(10);
		order2.setPrice(5.0);
		orderList.add(order);
		orderList.add(order2);
		session.write(orderList);
    }
 
    public void send(Object message) {
        session.write(message);
    }
 
    public boolean close() {
    	session.close(true);
        connector.dispose();
        return true;
    }
 
    public SocketConnector getConnector() {
        return connector;
    }
 
    public IoSession getSession() {
        return session;
    }
    
    public static void main(String[] args) {
		MinaClient.getInstance();
		MinaClient.getInstance().sendDemoMsg();
	}
}
