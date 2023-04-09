package com.lament.z.drop.cmd;

import com.lament.z.drop.netty.client.InnerClient;

import org.springframework.shell.standard.FileValueProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class DropCMD {
	@ShellMethod(value = "Drop [file] to [target host]")
	public void drop(@ShellOption(valueProvider = FileValueProvider.class) String name,
			String host){
		InnerClient client = new InnerClient(host,9332);
		client.sendFile(name);
		System.out.printf("Drop [ %s ] to [ %s:%s ] Done. %n",name,host,"9332");
	}

}
