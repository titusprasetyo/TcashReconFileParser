package com.agit.tcash.recon.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.agit.tcash.recon.model.BMRStatement;
import com.agit.tcash.recon.model.BNIStatement;
import com.github.ffpojo.FFPojoHelper;

public class ParseTest {

	private static final String THE_FILE_BMR = "D:\\Ag-IT\\Telkomsel\\TCash\\TCash Reconcile\\Reference\\1240004904539.txt";
	private static final String THE_FILE_BNI = "D:\\Ag-IT\\Telkomsel\\TCash\\TCash Reconcile\\Reference\\BNIjurnal.txt";
	
	public static void main(String[] args) throws Exception {
		ParseTest test = new ParseTest();
		try {
			test.readBNIStatement();
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readBMRStatement() throws Exception {
		FFPojoHelper ffpojo = FFPojoHelper.getInstance();
		BufferedReader textFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(THE_FILE_BMR)));
		String line;
		while ((line = textFileReader.readLine()) != null) {
			BMRStatement bmr = ffpojo.createFromText(BMRStatement.class, line);
			System.out.printf("[%tc] [%s] [%s] [%f] [%f]\n", bmr.getDateTime(), bmr.getDescription(),
					bmr.getReferenceNo(), bmr.getDebit(), bmr.getCredit());
		}
		textFileReader.close();
	}

	public void readBNIStatement() throws Exception {
		FFPojoHelper ffpojo = FFPojoHelper.getInstance();
		BufferedReader textFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(THE_FILE_BNI)));
		String line;
		String desc="";
		String[] tmp;
		int i = 1;
		while ((line = textFileReader.readLine()) != null) {
			if (i >= 6) {
				tmp = line.split("\\|");
				if (tmp.length > 8) {
					desc="";
					for (int c = 4; c < tmp.length - 3; c++) {
						desc += tmp[c].trim() + " ";
					}
					line = tmp[0] + "|" + tmp[1] + "|" + tmp[2] + "|" + tmp[3] + "|" + desc + "|"
							+ tmp[tmp.length - 3] + "|" + tmp[tmp.length - 2] + "|" + tmp[tmp.length - 1];
				}
				BNIStatement bni = ffpojo.createFromText(BNIStatement.class, line);
				System.out.printf("[%tc] [%s] [%s] [%s] [%f] [%s] [%f]\n", bni.getPostDate(), bni.getBranch(),
						bni.getJournalNo(), bni.getDescription(), bni.getAmount(), bni.getDbcr(), bni.getBalance());
			}
			i++;
		}
		textFileReader.close();
	}
}
