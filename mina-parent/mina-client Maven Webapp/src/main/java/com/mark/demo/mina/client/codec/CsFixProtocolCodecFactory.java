package com.mark.demo.mina.client.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/*
 *@author(huangxiaoping)
 *@date 2013-10-24
 */
public class CsFixProtocolCodecFactory implements ProtocolCodecFactory {
	private CsFixProtocolDecoder decoder;
	private CsFixProtocolEncoder encoder;
	
	public CsFixProtocolCodecFactory(Charset charset){
		decoder= new CsFixProtocolDecoder(charset);
		encoder=new CsFixProtocolEncoder(charset);
	}
	
	
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		
		return decoder;
	}

	
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		
		return encoder;
	}
	

}
