package com.mark.demo.mina.server.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.mark.demo.mina.server.entity.Result;

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
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);  
        CharsetEncoder encoder = charset.newEncoder(); 
        /**
		 *total=111\n
		 */
        Result result=(Result)msg;
        
        buf.putString("total="+result.getTotal()+"\n", encoder);
        
        buf.flip();  
        out.write(buf);
 
	}

	
}
