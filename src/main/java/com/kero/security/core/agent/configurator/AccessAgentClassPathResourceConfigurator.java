package com.kero.security.core.agent.configurator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.kero.security.core.agent.KeroAccessAgent;
import com.kero.security.core.agent.configuration.KeroAccessAgentConfigurator;
import com.kero.security.core.scheme.configurator.KsdlAccessSchemeConfigurator;
import com.kero.security.lang.provider.CompositeProvider;
import com.kero.security.lang.provider.KsdlProvider;
import com.kero.security.lang.provider.TextualProvider;
import com.kero.security.lang.provider.resource.FileResource;
import com.kero.security.lang.provider.resource.KsdlTextResource;

@Component
public class AccessAgentClassPathResourceConfigurator implements KeroAccessAgentConfigurator {

	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Spring");
	
	@Value("${kero.security.lang.resource.cache.enabled:true}")
	private boolean resourceCacheEnabled;
	
	@Value("${kero.security.lang.provider.cache.enabled:true}")
	private boolean providerCacheEnabled;

	@Value("${kero.security.lang.file.suffixes:**/*.k-s,**/*.ks}")
	private String[] ksdlFileSuffixes;
	
	@Override
	public void configure(KeroAccessAgent agent) {
	
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		
		Set<KsdlProvider> sources = new HashSet<>();
		
		int totalFilesFound = 0;
		
		for(String suffix : ksdlFileSuffixes) {
			
			try {
				
				LOGGER.debug("Begin scan classpath with pattern: classpath*:"+suffix);
				
				Resource[] resources = resolver.getResources("classpath*:"+suffix);
			
				totalFilesFound += resources.length;
				
				for(Resource resource : resources) {
			
					KsdlTextResource ksdlResource = new FileResource(resource.getFile());
					
					if(resourceCacheEnabled) {
						
						ksdlResource = KsdlTextResource.addCacheWrap(ksdlResource);
						LOGGER.debug("Found KSDL file resource: "+resource.getFilename());
					}
					
					sources.add(new TextualProvider(ksdlResource));
				}
				
			}
			catch (IOException e) {

				e.printStackTrace();
			}
		}
	
		LOGGER.info("Found KSDL files in classpath: "+totalFilesFound);
		
		KsdlProvider provider = new CompositeProvider(sources);
		
		if(providerCacheEnabled) {
			
			provider = KsdlProvider.addCacheWrap(provider);
		}
		
		agent.addConfigurator(new KsdlAccessSchemeConfigurator(provider));
	}
}
