package com.kero.security.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.agent.configurator.KeroAccessAgentConfigruatorBeans;
import com.kero.security.spring.config.KeroAccessAgentConfiguration;
import com.kero.security.spring.config.KeroAccessAgentFactoryConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KeroAccessAgentConfiguration.class, KeroAccessAgentFactoryConfiguration.class, KeroAccessAgentConfigruatorBeans.class})
@ActiveProfiles("test")
public class MainTest {
	
	@Autowired
	private KeroAccessAgent agent;
	
	@Test
	public void test() {
		
		/*
		AccessRule defaultRule = agent.getOrCreateScheme(TestObjectImpl.class).getOrCreateLocalProperty("text").getDefaultRule();
		
		System.out.println("defaultRule: "+defaultRule);
		
		TestObjectImpl obj = new TestObjectImpl("kek");
			obj.getChilds().add(new TestObjectImpl("lol"));
		
		TestObjectImpl ms = agent.protect(obj, "MS");
		
		assertThrows(AccessException.class, ms::getText);
	
		TestObjectImpl owner = agent.protect(obj, "OWNER");
		
		assertDoesNotThrow(owner::getText);
		
		assertThrows(AccessException.class, owner.getChilds().get(0)::getText);
		
		TestObjectImpl common = agent.protect(obj, "COMMON");
		
		assertThrows(AccessException.class, owner::getText);
		*/
	}
}
