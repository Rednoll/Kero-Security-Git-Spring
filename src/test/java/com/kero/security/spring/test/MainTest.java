package com.kero.security.spring.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.agent.configurator.KeroAccessAgentConfiguratorBeans;
import com.kero.security.core.exception.AccessException;
import com.kero.security.spring.config.KeroAccessAgentBean;
import com.kero.security.spring.config.KeroAccessAgentFactoryBean;
import com.kero.security.spring.config.KeroAccessAgentFactoryGitConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KeroAccessAgentBean.class, KeroAccessAgentFactoryGitConfiguration.class, KeroAccessAgentConfiguratorBeans.class, KeroAccessAgentFactoryBean.class})
@ActiveProfiles("test")
@Ignore
public class MainTest {
	
	@Autowired
	private KeroAccessAgent agent;
	
	@Test
	public void test() {
		
		TestGitObject ownerProtected = agent.protect(new TestGitObject(), "OWNER");
		
		assertDoesNotThrow(ownerProtected::getText);
		assertDoesNotThrow(ownerProtected::getObj2);
	
		TestGitObject msProtected = agent.protect(new TestGitObject(), "MS");
		
		assertThrows(AccessException.class, msProtected::getObj2);
		
		TestGitObject2 obj2 = agent.protect(new TestGitObject2(), "OWNER");
		
		assertThrows(AccessException.class, obj2::getKek);
	}
}
