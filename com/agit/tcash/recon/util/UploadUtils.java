package com.agit.tcash.recon.util;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.UUID;

public class UploadUtils {
	private static FileInputStream fis;

	public static String generateUUID() {
		return String.valueOf(UUID.randomUUID());
	}

	public static String getChecksumString(String filename) throws Exception {
		String datafile = filename;

		MessageDigest md = MessageDigest.getInstance("SHA1");
		fis = new FileInputStream(datafile);
		byte[] dataBytes = new byte[1024];

		int nread = 0;

		while ((nread = fis.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		}
		;

		byte[] mdbytes = md.digest();

		// convert the byte to hex format
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		fis.close();
		return sb.toString();
	}
}
