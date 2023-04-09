package com.lament.z.drop.cmd;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lament.z.drop.netty.server.InnerServer;

import org.springframework.shell.standard.FileValueProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ConfigCMD {

	@ShellMethod(value = "Change settings",key = "config",group = "Settings")
	public void config(@ShellOption(valueProvider = FileValueProvider.class)String d){
		Path path = Paths.get(d);
		if (Files.isDirectory(path)){
			InnerServer.getServer().setDefaultDir(d);
			System.out.printf("Default Dir: %s%n",d);
		}else {
			System.err.printf("Invalid path: %s%n",d);
		}
	}
}
