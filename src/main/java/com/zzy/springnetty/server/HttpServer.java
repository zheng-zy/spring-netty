package com.zzy.springnetty.server;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;

/**
 * HttpServer.java
 * 
 * @desc
 * @author zhengzy
 * @date 2016年1月12日上午9:51:51
 */
@Component
public class HttpServer {

	private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

	@Value("${http.port}")
	private String httpPort;

	@Autowired
	@Qualifier("serverBootstrap")
	private ServerBootstrap serverBootstrap;
	
	@Autowired
	@Qualifier("port")
	private Integer port;

	@PostConstruct
	public void start() throws Exception {
		logger.info("http server is starting.");
		Channel ch = serverBootstrap.bind(port).sync().channel();
		logger.info("http server is started at port {}.", port);
		logger.info("open your browser and navigate to http://localhost:{}.", port);
		ch.closeFuture().sync();
	}


}
