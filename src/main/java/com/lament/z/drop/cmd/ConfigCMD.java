package com.lament.z.drop.cmd;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lament.z.drop.netty.server.InnerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.shell.standard.FileValueProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ConfigCMD {
	Logger log = LoggerFactory.getLogger(ConfigCMD.class);
	@ShellMethod(value = "Change settings",key = "config",group = "Settings")
	public void config(@ShellOption(valueProvider = FileValueProvider.class)String d){
		Path path = Paths.get(d);
		if (Files.isDirectory(path)){
			InnerServer.getServer().setDefaultDir(d);
			log.info("Default Dir: %s%n",d);
		}else {
			log.error("Invalid path: %s%n",d);
		}
	}
}
