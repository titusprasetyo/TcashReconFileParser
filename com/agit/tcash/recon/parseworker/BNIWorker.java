/*
 * Class worker for parsing BNI statement file
 * Parsing using ffpojo library
 * Dev by : Titus Adi Prasetyo
 * 
 */

package com.agit.tcash.recon.parseworker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.agit.tcash.recon.dao.ParseDao;
import com.agit.tcash.recon.model.BNIStatement;
import com.github.ffpojo.FFPojoHelper;

public class BNIWorker {
	private String THE_FILE;
	private String guuid;

	public BNIWorker() {

	}

	public BNIWorker(String file, String uuid) {
		this.THE_FILE = file;
		this.guuid = uuid;
	}

	/*
	 * read BNI statement file
	 */
	public String readBNIStatement() {

		// define required variable
		String line;
		String desc = "";
		String output = "";
		String[] tmp;
		FFPojoHelper ffpojo = null;
		BufferedReader textFileReader = null;
		ParseDao dao = null;
		BNIStatement bni = null;
		int i = 1;

		// initiate ffpojo instance
		try {
			ffpojo = FFPojoHelper.getInstance();
		} catch (Exception e) {
			output = "Error initiating Parser Helper";
			return output;
		}

		// initiate BNI statement file using BufferedReader
		try {
			textFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(THE_FILE)));
		} catch (Exception e) {
			output = "Error reading file : " + e.getMessage();
			return output;
		}

		// start reading line by line
		try {
			while ((line = textFileReader.readLine()) != null) {
				// start reading line >= 6 that contain statement data
				if (i >= 6) {
					tmp = line.split("\\|");
					// the BNI file is inconsistent of field count
					// when field count greater than 8, get 4 first field and 3
					// last field. The middle field concate as description data
					if (tmp.length > 8) {
						desc = "";
						for (int c = 4; c < tmp.length - 3; c++) {
							desc += tmp[c].trim() + " ";
						}
						line = tmp[0] + "|" + tmp[1] + "|" + tmp[2] + "|" + tmp[3] + "|" + desc + "|"
								+ tmp[tmp.length - 3] + "|" + tmp[tmp.length - 2] + "|" + tmp[tmp.length - 1];
					}
					try {
						// parse the line using fpojo
						bni = ffpojo.createFromText(BNIStatement.class, line);
						bni.setDtStamp();
						// output += bni.getDescription() + "\n";
						// start save to DB for parsed data
						try {
							dao = new ParseDao();
							if ("d".equalsIgnoreCase(bni.getDbcr()))
								dao.saveToDb(bni, "bni", guuid);
						} catch (Exception e) {
							output += "Error saving to db for line " + i + " : " + e.getMessage();
						}
					} catch (Exception e) {
						output += "Error parsing line " + i + " : " + e.getMessage();
					}
				}
				i++;
			}
		} catch (Exception e) {
			output += "Error reading line " + i + " : " + e.getMessage();
		}

		// close the file reader
		try {
			textFileReader.close();
		} catch (Exception e) {
			output = "Error Closing read file";
		}

		output += "Parsing BNI file finish";
		return output;
	}
}
