package com.yang.server;

import com.yang.server.common.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(ServerApplication.class, args);
		SpringUtil.setApplicationContext(app);
	}
}
