/*
 * Class worker for parsing Bank Mandiri statement file
 * Parsing using ffpojo library
 * Dev by : Titus Adi Prasetyo
 * 
 */

package com.agit.tcash.recon.parseworker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import com.agit.tcash.recon.dao.ParseDao;
import com.agit.tcash.recon.model.BMRStatement;
import com.github.ffpojo.FFPojoHelper;

public class BMRWorker {

	private String THE_FILE;
	private String guuid;

	public BMRWorker() {

	}

	public BMRWorker(String file, String uuid) {
		this.THE_FILE = file;
		this.guuid = uuid;
	}

	/*
	 * read Bank Mandiri statement file
	 */
	public String readBMRStatement() throws Exception {

		// define required variable
		String output = "";
		FFPojoHelper ffpojo = null;
		String line = null;
		int i = 0;
		BufferedReader textFileReader = null;
		ParseDao dao = null;
		BMRStatement bmr = null;

		// initiate ffpojo instance
		try {
			ffpojo = FFPojoHelper.getInstance();
		} catch (Exception e) {
			output = "Error initiating Parser Helper";
			return output;
		}

		// initiate Bank Mandiri statement file using BufferedReader
		try {
			textFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(THE_FILE)));
		} catch (Exception e) {
			output = "Error reading file : " + e.getMessage();
			return output;
		}

		// start reading line by line
		try {
			while ((line = textFileReader.readLine()) != null) {
				if (!"".equalsIgnoreCase(line)) {
					try {
						// parse using ffpojo
						bmr = ffpojo.createFromText(BMRStatement.class, line);
						bmr.setDtStamp();
						// output += bmr.getDescription() + "\n";
						// start save to database for parsed data
						try {
							dao = new ParseDao();
							// if (bmr.getDebit().compareTo(BigDecimal.ZERO) !=
							// 0)
							dao.saveToDb(bmr, "bmr", guuid);
						} catch (Exception e) {
							output += "Error saving to db for line " + i + " : " + e.getMessage();
						}
					} catch (Exception e) {
						output += "Error parsing line " + i + " : " + e.getMessage();
					}
					i++;
				}
			}
		} catch (Exception e) {
			output += "Error reading line " + i + " : " + e.getMessage();
		}
		output += "Parsing Bank Mandiri file finish";
		// close the file reader
		textFileReader.close();
		return output;
	}
}
