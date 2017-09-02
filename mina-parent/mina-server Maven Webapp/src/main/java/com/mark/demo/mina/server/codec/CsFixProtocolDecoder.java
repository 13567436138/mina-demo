package com.mark.demo.mina.server.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.mark.demo.mina.server.entity.Order;

/*
 *@author(huangxiaoping)
 *@date 2013-10-24
 */
public class CsFixProtocolDecoder  extends ProtocolDecoderAdapter {
	private static final Logger logger=Logger.getLogger(CsFixProtocolDecoder.class);
	private final Charset charset;

	public CsFixProtocolDecoder(Charset charset){
		this.charset=charset;
	}
	
	
	public void decode(IoSession ioSession, IoBuffer in, ProtocolDecoderOutput protocolDecoderOutput)
			throws Exception {
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();  
		/**
		 *item=aaa&price=11&number=10:item=aaa&price=11&number=10\n
		 */
		List<Order> orderList=new ArrayList<Order>();
		
		while(in.hasRemaining()){  
            byte b = in.get();  
            if(b == 10 ){
            	buf.flip(); 
            	String orders=buf.getString(decoder);
            	String[] orderAry=orders.split(":");
            	for(int i=0;i<orderAry.length;i++){
            		Order orderEntity=new Order();
            		String order=orderAry[i];
            		String[]fields=order.split("&");
            		for(int j=0;j<fields.length;j++){
            			String field=fields[j];
            			String[] fieldKV=field.split("=");
            			if(fieldKV[0].equals("item")){
            				orderEntity.setItem(fieldKV[1]);
            			}else if(fieldKV[0].equals("price")){
            				orderEntity.setPrice(Double.parseDouble(fieldKV[1]));
            			}else if(fieldKV[0].equals("number")){
            				orderEntity.setNumber(Integer.parseInt(fieldKV[1]));
            			}
            		}
            		orderList.add(orderEntity);
            	}
            	buf.clear();  
            	
            }else{
            	buf.put(b);
            }
		}
		
		protocolDecoderOutput.write(orderList);   
	}

}
