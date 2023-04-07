package com.lament.z.drop.netty.common.codec;

import java.util.List;

import com.lament.z.drop.netty.pojo.DropFile;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;


/**
 * Use {@link ReplayingDecoder} and POJO to avoid packet fragmentation problem.
 * <p>
 * However this cause a tiny assembly problem, need manually release a ByteBuf
 * otherwise it will cause a leak.
 *
 * */
public class DropFileDecoder extends ReplayingDecoder<DropFile> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		DropFile dropFile = new DropFile();
		ByteBuf nameBuf = in.readBytes(in.readInt());
		dropFile.setFileName(new String(ByteBufUtil.getBytes(nameBuf)));
		nameBuf.release(); // release this buf ,other wise will be a leak

		byte[] payload = ByteBufUtil.getBytes(in.readBytes(in.readableBytes()));
		dropFile.setPayload(payload);
		out.add(dropFile);
	}
}
