/*
 * 
 * Main class for parsing operation
 * Dev by : Titus Adi Prasetyo
 * 
 */

package com.agit.tcash.recon.parseworker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Parser {

	public Parser() {

	}

	/*
	 * Start parsing the uploaded file Parameter : 1. filename : the filename of
	 * uploaded object
	 * 
	 */
	public String doParse(String filename, String uuid) throws Exception {
		// define required variable
		String output = "";
		// read the first line of uploaded file to identify BNI file or Bank
		// Mandiri file
		BufferedReader textFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		String line = textFileReader.readLine();
		// BNI file for first line contain PT BANK NEGARA INDONESIA (PERSERO)
		// TBK.
		if (line.contains("PT BANK NEGARA INDONESIA (PERSERO) TBK.")) {
			output = doParseBNI(filename, uuid);
		} else if (line.startsWith("1240004904539")) {
			output = doParseBMR(filename, uuid);
		} else {
			output = "Uploaded file is not BNI or Bank Mandiri statement";
		}
		textFileReader.close();
		return output;
	}

	/*
	 * Start parsing the uploaded file when it's BNI file Parameter : 1.
	 * filename : the filename of uploaded object
	 * 
	 */
	private String doParseBNI(String filename, String uuid) throws Exception {
		String output = "";
		BNIWorker bni = new BNIWorker(filename, uuid);
		output = bni.readBNIStatement();
		return output;
	}

	/*
	 * Start parsing the uploaded file when it's Bank Mandiri file Parameter :
	 * 1. filename : the filename of uploaded object
	 * 
	 */
	private String doParseBMR(String filename, String uuid) throws Exception {
		String output = "";
		BMRWorker bmr = new BMRWorker(filename, uuid);
		output = bmr.readBMRStatement();
		return output;
	}

}
