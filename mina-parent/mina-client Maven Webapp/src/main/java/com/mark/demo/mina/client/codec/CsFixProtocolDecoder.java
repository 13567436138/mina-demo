package com.mark.demo.mina.client.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.mark.demo.mina.client.entity.Result;

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
		 *total=111\n
		 */
		Result result=new Result();
		while(in.hasRemaining()){  
            byte b = in.get();  
            if(b == 10 ){
            	buf.flip(); 
            	String total=buf.getString(decoder);
            	String[] totalAry=total.split("=");
            	result.setTotal(Double.parseDouble(totalAry[1]));
            	buf.clear();  
            	
            }else{
            	buf.put(b);
            }
		}
		
		protocolDecoderOutput.write(result);   
	}

}
