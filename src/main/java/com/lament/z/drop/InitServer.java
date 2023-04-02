package com.lament.z.drop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lament.z.drop.netty.server.InnerServer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Component
public class InitServer {
	Logger log = LoggerFactory.getLogger(InnerServer.class);

	@PostConstruct
	public void init(){

		log.info("Server init and start.");
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			InnerServer server = InnerServer.getServer();
			server.run();
		});
	}
}
