package com.lament.z.drop;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lament.z.drop.netty.server.InnerServer;
import jakarta.annotation.PostConstruct;
import org.jline.terminal.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;



@Component
public class InitServer {
	Logger log = LoggerFactory.getLogger(InitServer.class);

	final
	Terminal terminal;

	public InitServer(Terminal terminal) {
		this.terminal = terminal;
	}

	@PostConstruct
	public void init(){
		if (log.isDebugEnabled()){
			log.debug("------- System Properties ------------");
			Properties properties = System.getProperties();
			for (String propertyName : properties.stringPropertyNames()) {
				log.debug("{} | {}", propertyName, properties.get(propertyName));
			}
			log.debug("-------------- End --------------");
		}

		log.info("Terminal type: {}", terminal.getName());
		log.info("Terminal encoding: {}", terminal.encoding().displayName());

		if (log.isDebugEnabled()){
			log.debug("Server init and start.");
		}

		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			InnerServer server = InnerServer.getServer();
			server.run();
		});
	}
}
