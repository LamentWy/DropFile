package com.lament.z.drop.netty.server;

import com.lament.z.drop.util.SystemUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnerServerTests {

	private InnerServer server = InnerServer.getServer();

	@Test
	void getServer() {
		InnerServer server = InnerServer.getServer();
		assertEquals(this.server,server);
	}

	@Test
	void getDefaultDir() {
		String dir = server.getDefaultDir();
		if (SystemUtil.IS_MAC){
			assertEquals(SystemUtil.USER_DIR+InnerServer.MAC,dir);
		}

		if (SystemUtil.IS_WINDOWS){
			assertEquals(SystemUtil.USER_DIR+InnerServer.WINDOWS,dir);
		}
	}

	@Test
	void setDefaultDir() {
	}

	@Test
	void run() {
	}

	@Test
	void shutdown() {
	}
}