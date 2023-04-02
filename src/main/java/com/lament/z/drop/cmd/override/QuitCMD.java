package com.lament.z.drop.cmd.override;

import com.lament.z.drop.netty.server.InnerServer;

import org.springframework.shell.ExitRequest;
import org.springframework.shell.context.InteractionMode;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class QuitCMD implements Quit.Command{
	@ShellMethod(value = "ShutDown InnerServer and Exit the shell.", key = {"quit", "exit"}, group = "Built-In Commands", interactionMode = InteractionMode.INTERACTIVE)
	public void quit() {
		InnerServer.getServer().shutdown();
		throw new ExitRequest();
	}
}
