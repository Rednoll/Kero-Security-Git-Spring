package com.kero.security.spring.config;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.kero.security.core.agent.configurator.KsdlAgentGitResourceConfigurator;
import com.kero.security.ksdl.agent.KsdlAgentFactory;

@Configuration
public class KeroAccessAgentFactoryGitConfiguration implements KsdlAgentFactorySpringConfiguration {
	
	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Git-Spring");
	
	@Value("${kero.security.lang.resource.git.remote:#{null}}")
	private String rawRemote;
	
	@Value("${kero.security.lang.resource.git.branch:#{null}}")
	private String branch;
	
	@Value("${kero.security.lang.resource.git.token:#{null}}")
	private String token;
	
	@Value("${kero.security.lang.resource.git.username:#{null}}")
	private String username;
	
	@Value("${kero.security.lang.resource.git.pass:#{null}}")
	private String pass;
	
	@Value("${kero.security.lang.resource.git.suffixes:.k-s,.ks}")
	private String[] rawSuffixes;

	@Override
	public void configure(KsdlAgentFactory factory) {
		
		if(this.rawRemote == null
		&& this.branch == null) return;
		
		if(this.rawRemote == null) throw new RuntimeException("kero.security.lang.resource.git.remote not specified!");
		if(this.branch == null) throw new RuntimeException("kero.security.lang.resource.git.branch not specified!");
		
		CredentialsProvider credentials = null;
		
		if(this.token != null) {
			
			credentials = new UsernamePasswordCredentialsProvider(this.token, "");
		}
		else if(this.username != null && this.pass != null) {
			
			credentials = new UsernamePasswordCredentialsProvider(this.username, this.pass);
		}
		
		try {
			
			URI remote = new URI(rawRemote);
			String branch = this.branch;
			Set<String> suffixes = new HashSet<>(Arrays.asList(this.rawSuffixes));
			
			KsdlAgentGitResourceConfigurator conf =
				new KsdlAgentGitResourceConfigurator(credentials, remote, branch, suffixes);
		
			factory.addConfigurator(conf);
			
			StringBuilder debugLog = new StringBuilder();
				debugLog.append("Successful add AccessAgentGitResourceConfigurator:");
				debugLog.append("\n  remote: "+remote);
				debugLog.append("\n  branch: "+branch);
				debugLog.append("\n  token: "+this.token);
				debugLog.append("\n  username: "+this.username);
				debugLog.append("\n  pass: "+this.pass);
				
			LOGGER.debug(debugLog.toString());
		}
		catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}
}