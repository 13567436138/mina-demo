package com.mark.demo.mina.client.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.mark.demo.mina.client.entity.Order;

/*
 *@author(huangxiaoping)
 *@date 2013-10-24
 */
public class CsFixProtocolEncoder extends ProtocolEncoderAdapter {
	private final Charset charset;
	public CsFixProtocolEncoder(Charset charset){
		this.charset=charset;
	}
	
	
	public void encode(IoSession session, Object msg, ProtocolEncoderOutput out)
			throws Exception {
		List<Order> orderList=(List<Order>)msg;
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);  
        CharsetEncoder encoder = charset.newEncoder(); 
        
        /**
		 *item=aaa&price=11&number=10:item=aaa&price=11&number=10\n
		 */
        for(int i=0;i<orderList.size()-1;i++){
        	Order order=orderList.get(i);
	        buf.putString("item="+order.getItem()+"&", encoder);
	        buf.putString("price="+order.getPrice()+"&", encoder);
	        buf.putString("number="+order.getNumber()+":", encoder);
        }
        
        Order order=orderList.get(orderList.size()-1);
        buf.putString("item="+order.getItem()+"&", encoder);
        buf.putString("price="+order.getPrice()+"&", encoder);
        buf.putString("number="+order.getNumber()+"\n", encoder);
        
        buf.flip();  
        out.write(buf);
	}

	
}
