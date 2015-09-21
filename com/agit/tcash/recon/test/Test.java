package com.agit.tcash.recon.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Test {
	private static final String THE_FILE_BMR = "D:\\Ag-IT\\Telkomsel\\TCash\\TCash Reconcile\\Reference\\1240004904539.txt";
	private static BufferedReader textFileReader;

	public static void main(String[] args) {
		try {
			textFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(THE_FILE_BMR)));
			String line = textFileReader.readLine();
			System.out.println(line);
			if (line.contains("PT BANK NEGARA INDONESIA (PERSERO) TBK.")){
				System.out.println("BNI");
			}else{
				System.out.println("MANDIRI");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
