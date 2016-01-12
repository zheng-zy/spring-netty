package com.zzy.springnetty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.zzy.springnetty.cfg.SpringConfig;

/**
 * Main.java
 * 
 * @desc 程序入口
 * @author zhengzy
 * @date 2016年1月4日下午3:04:52
 */
public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) {
		logger.info("Starting application context.");
		@SuppressWarnings("resource")
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		ctx.registerShutdownHook();
	}
}
