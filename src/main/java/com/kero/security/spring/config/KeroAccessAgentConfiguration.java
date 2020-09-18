package com.kero.security.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.agent.KeroAccessAgentFactory;

@Configuration
public class KeroAccessAgentConfiguration {

	@Autowired
	private KeroAccessAgentFactory factory;
	
	@Bean
	public KeroAccessAgent keroAccessAgent() {
		
		return factory.create();
	}
}
