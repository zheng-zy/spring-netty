package com.zzy.springnetty.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * HttpProtocolInitalizer.java 
 * @desc   初始化协议处理
 * @author zhengzy
 * @date   2016年1月4日下午4:34:42
 */

@Component
@Qualifier("httpProtocolInitalizer")
public class HttpProtocolInitalizer extends ChannelInitializer<SocketChannel>{
	
	@Autowired
	private HttpServerInboundHandler httpServerInboundHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
		pipeline.addLast("encoder", new HttpResponseEncoder());
//		pipeline.addLast("decoder", httpRequestDecoder);
//		pipeline.addLast("aggregator", httpObjectAggregator);
//		pipeline.addLast("encoder", httpResponseEncoder);
		pipeline.addLast("handler", httpServerInboundHandler);
	}
	
	
	
}
