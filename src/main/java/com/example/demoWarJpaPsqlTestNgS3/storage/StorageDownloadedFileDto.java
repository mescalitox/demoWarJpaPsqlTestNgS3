/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3.storage;

import java.io.Serializable;

/**
 * @author jaubert
 *
 */
public class StorageDownloadedFileDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1902254700268337942L;

	private byte[] file;

	private String mime;

	/**
	 * @return the file
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}

	/**
	 * @return the mime
	 */
	public String getMime() {
		return mime;
	}

	/**
	 * @param mime
	 *            the mime to set
	 */
	public void setMime(String mime) {
		this.mime = mime;
	}

}
