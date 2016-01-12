package com.zzy.springnetty.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.zzy.springnetty.handlers.HttpProtocolInitalizer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * SpringConfig.java
 * 
 * @desc 扫描当前文件配置的包中注解的bean，自动装配，netty的配置文件采用PropertySource注入读取
 * @author zhengzy
 * @date 2016年1月4日下午3:04:18
 */
@Configuration
@ComponentScan("com.zzy.springnetty")
@PropertySource("classpath:netty-server.properties")
public class SpringConfig {
	
	private final int MAX_CONTENT_LENGTH = 65536;

	@Value("${boss.thread.count}")
	private int bossCount;

	@Value("${worker.thread.count}")
	private int workerCount;

	@Value("${http.port}")
	private int httpPort;

	@Value("${so.keepalive}")
	private boolean keepAlive;

	@Value("${so.backlog}")
	private int backlog;

	@Value("${log4j.configuration}")
	private String log4jConfiguration;
	
	@Autowired
	private HttpProtocolInitalizer httpProtocolInitalizer;

	@Bean(name = "serverBootstrap")
	public ServerBootstrap bootstrap() {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup(), workerGroup()).channel(NioServerSocketChannel.class).childHandler(httpProtocolInitalizer);
//		Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
//		Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
//		for (@SuppressWarnings("rawtypes")
//		ChannelOption option : keySet) {
//			b.option(option, tcpChannelOptions.get(option));
//		}
		return b;
	}

	@Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(bossCount);
	}

	@Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(workerCount);
	}

	@Bean(name = "port")
	public Integer httpPort() {
		return httpPort;
	}

//	@Bean(name = "tcpChannelOptions")
//	public Map<ChannelOption<?>, Object> tcpChannelOptions() {
//		Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
//		options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
//		options.put(ChannelOption.SO_BACKLOG, backlog);
//		return options;
//	}

	
//	/**
//	 * http解码器
//	 * @return
//	 */
//	@Bean(name = "httpRequestDecoder")
//	public HttpRequestDecoder getHttpRequestDecoder() {
//		return new HttpRequestDecoder();
//	}
//	/**
//	 * HttpObjectAggregator会把多个消息转换为一个
//	 * 单一的FullHttpRequest或是FullHttpResponse
//	 * @return
//	 */
//	@Bean(name = "httpObjectAggregator")
//	public HttpObjectAggregator getHttpObjectAggregator() {
//		return new HttpObjectAggregator(MAX_CONTENT_LENGTH);
//	}
//	/**
//	 * http编码器
//	 * @return
//	 */
//	@Bean(name = "httpResponseEncoder")
//	public HttpResponseEncoder getHttpResponseEncoder() {
//		return new HttpResponseEncoder();
//	}

	/**
	 * Necessary to make the Value annotations work.
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
