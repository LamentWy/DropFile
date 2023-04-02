package com.lament.z.drop.netty.common.codec;

import java.nio.charset.StandardCharsets;

import com.lament.z.drop.netty.pojo.DropFile;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class DropFileEncoder extends MessageToByteEncoder<DropFile> {
	@Override
	protected void encode(ChannelHandlerContext ctx, DropFile msg, ByteBuf out) throws Exception {
		int nameLength = msg.getFileName().getBytes(StandardCharsets.UTF_8).length;
		out.writeInt(nameLength);
		out.writeBytes(msg.getFileName().getBytes(StandardCharsets.UTF_8));
		out.writeBytes(msg.getPayload());
	}
}
