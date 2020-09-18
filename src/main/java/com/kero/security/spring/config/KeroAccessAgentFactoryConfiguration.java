package com.kero.security.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kero.security.core.agent.KeroAccessAgentFactory;
import com.kero.security.core.agent.KeroAccessAgentFactoryImpl;
import com.kero.security.core.agent.configurator.AccessAgentClassPathResourceConfigurator;

@Configuration
public class KeroAccessAgentFactoryConfiguration {
		
	@Value("${kero.security.lang.resource.classpath.enabled:true}")
	private boolean resourceClassPathEnabled;
	
	@Autowired
	private AccessAgentClassPathResourceConfigurator classPathResourceConfigurator;
	
	@Bean
	public KeroAccessAgentFactory keroAccessAgentFactory() {
		
		KeroAccessAgentFactory factory = new KeroAccessAgentFactoryImpl();
	
		if(resourceClassPathEnabled) {
			
			factory.addConfigurator(classPathResourceConfigurator);
		}
		
		return factory;
	}
}