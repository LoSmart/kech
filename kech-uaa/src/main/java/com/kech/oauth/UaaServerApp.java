package com.kech.oauth;

import com.kech.common.annotation.EnableLoginArgResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/** 
* @author mall
*/
@EnableFeignClients
@EnableDiscoveryClient
@EnableRedisHttpSession
@EnableLoginArgResolver
@SpringBootApplication
@ComponentScan({"com.kech.common","com.kech.oauth2"})
public class UaaServerApp {
	public static void main(String[] args) {
		SpringApplication.run(UaaServerApp.class, args);
	}
}
