package com.angelhack.core;

import java.io.File;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.net.Uri;

public class Attachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 21L;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	byte[] byteArray;

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	String name = "";
	String mimeType = "";
	long size;
	long attachmentSize = 0;
	String type = "";
	File file;
	String displaySize = "";
	String serverPath = "";
	String url = "";
	int Id=-1;
	Bitmap bitmap;
	String thumbNailUrl = "";
	byte[] thumnail;
	public byte[] getThumnail() {
		return thumnail;
	}

	public void setThumnail(byte[] thumnail) {
		this.thumnail = thumnail;
	}



	public String getThumbNailUrl() {
		return thumbNailUrl;
	}

	public void setThumbNailUrl(String thumbNailUrl) {
		this.thumbNailUrl = thumbNailUrl;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(String displaySize) {
		this.displaySize = displaySize;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Uri getUri() {
		return uri;
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}

	Uri uri;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(long attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

}
