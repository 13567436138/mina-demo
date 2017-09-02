package com.mark.demo.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.mark.demo.mina.server.codec.CsFixProtocolCodecFactory;

/*
 *@author(huangxiaoping)
 *@date 2013-9-17
 *mina服务器模块
 */
public class MainServer{
	private static final Logger logger=Logger.getLogger(MainServer.class);
	private static final int PORT = 8888;
	private IoAcceptor acceptor;
	private static volatile MainServer mainServer;
	
	public static MainServer getInstanse(){
		if(mainServer==null){
			synchronized (MainServer.class) {
				if(mainServer==null){
					mainServer=new MainServer();
				}
				
			}
		}
		return mainServer;
	}
	
	private MainServer(){}
	
	public void start() {
		acceptor = new NioSocketAcceptor();     
        LoggingFilter lf = new LoggingFilter("cs mina服务器");      
        acceptor.getFilterChain().addLast("logger", lf);     
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CsFixProtocolCodecFactory(Charset.forName("UTF-8"))));      
        acceptor.getSessionConfig().setReadBufferSize(1024);     
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);     
        acceptor.setHandler(new MainServerHandler());               
        try {
			acceptor.bind(new InetSocketAddress(PORT));
		} catch (IOException e) {
			logger.error("mina服务器绑定端口出错", e);
		}     
        System.out.println("start mina server");  
		
	}

	public void stop() {
		acceptor.dispose();
	}
	
	
	

}
