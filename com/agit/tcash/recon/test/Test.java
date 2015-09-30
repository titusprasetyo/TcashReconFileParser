package com.agit.tcash.recon.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
	private static final String THE_FILE_BMR = "D:\\Ag-IT\\Telkomsel\\TCash\\TCash Reconcile\\Reference\\1240004904539.txt";
	private static BufferedReader textFileReader;

	public static void main(String[] args) {
		System.out.println(getMd5Digest("cs"));
	}

	public static String getMd5Digest(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			return number.toString(16).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
