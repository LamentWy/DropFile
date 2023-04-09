package com.lament.z.drop.netty.pojo;

import java.util.Arrays;

public class DropFile {
	// xx.yy 主要是让 Server 知道是什么类型的文件
	private String fileName;
	// File 的字节流
	private byte[] payload;

	public DropFile() {
	}

	public DropFile(String fileName, byte[] payload) {
		this.fileName = fileName;
		this.payload = payload;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DropFile dropFile = (DropFile) o;

		if (!fileName.equals(dropFile.fileName)) return false;
		return Arrays.equals(payload, dropFile.payload);
	}

	@Override
	public int hashCode() {
		int result = fileName.hashCode();
		result = 31 * result + Arrays.hashCode(payload);
		return result;
	}
}
