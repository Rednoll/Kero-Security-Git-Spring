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

import com.kero.security.core.agent.KeroAccessAgentFactory;
import com.kero.security.core.agent.configurator.AccessAgentGitResourceConfigurator;

@Configuration
public class KeroAccessAgentFactoryGitConfiguration implements KeroAccessAgentFactorySpringConfiguration {
	
	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Git-Spring");
	
	@Value("${kero.security.lang.resource.git.remote:#{null}}")
	private String resourceGitRemote;
	
	@Value("${kero.security.lang.resource.git.branch:#{null}}")
	private String resourceGitBranch;
	
	@Value("${kero.security.lang.resource.git.token:#{null}}")
	private String resourceGitToken;
	
	@Value("${kero.security.lang.resource.git.username:#{null}}")
	private String resourceGitUsername;
	
	@Value("${kero.security.lang.resource.git.pass:#{null}}")
	private String resourceGitPass;
	
	@Value("${kero.security.lang.resource.git.suffixes:.k-s,.ks}")
	private String[] resourceGitSuffixes;
	
	@Value("${kero.security.lang.resource.cache.enabled:true}")
	private boolean resourceCacheEnabled;
	
	@Value("${kero.security.lang.provider.cache.enabled:true}")
	private boolean providerCacheEnabled;
	
	@Override
	public void configure(KeroAccessAgentFactory factory) {
		
		if(this.resourceGitRemote == null
		&& this.resourceGitBranch == null) return;
		
		if(this.resourceGitRemote == null) throw new RuntimeException("kero.security.lang.resource.git.remote not specified!");
		if(this.resourceGitBranch == null) throw new RuntimeException("kero.security.lang.resource.git.branch not specified!");
		
		CredentialsProvider credentials = null;
		
		if(this.resourceGitToken != null) {
			
			credentials = new UsernamePasswordCredentialsProvider(this.resourceGitToken, "");
		}
		else if(this.resourceGitUsername != null && this.resourceGitPass != null) {
			
			credentials = new UsernamePasswordCredentialsProvider(this.resourceGitUsername, this.resourceGitPass);
		}
		
		try {
			
			URI remote = new URI(resourceGitRemote);
			String branch = this.resourceGitBranch;
			Set<String> suffixes = new HashSet<>(Arrays.asList(this.resourceGitSuffixes));
			
			AccessAgentGitResourceConfigurator conf =
				new AccessAgentGitResourceConfigurator(credentials, remote, branch, resourceCacheEnabled, providerCacheEnabled, suffixes);
		
			factory.addConfigurator(conf);
			
			StringBuilder debugLog = new StringBuilder();
				debugLog.append("Successful add AccessAgentGitResourceConfigurator:");
				debugLog.append("\n  remote: "+remote);
				debugLog.append("\n  branch: "+branch);
				debugLog.append("\n  token: "+this.resourceGitToken);
				debugLog.append("\n  username: "+this.resourceGitUsername);
				debugLog.append("\n  pass: "+this.resourceGitPass);
				
			LOGGER.debug(debugLog.toString());
		}
		catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}
}