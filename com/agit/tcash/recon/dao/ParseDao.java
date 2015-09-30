/*
 * 
 * Data Access class for upload parsed object to DB
 * Dev by : Titus Adi Prasetyo
 * 
 */

package com.agit.tcash.recon.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.agit.tcash.recon.model.BMRStatement;
import com.agit.tcash.recon.model.BNIStatement;
import com.agit.tcash.recon.model.BaseModel;
import com.agit.tcash.recon.model.UploadLog;

public class ParseDao {

	private DataSource dataSource = null;
	private Connection connection = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String query = "";

	public ParseDao() throws SQLException {

	}

	private void init() throws SQLException, NamingException {
		Context ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/tsel_tunai");
		//dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/local");
		connection = dataSource.getConnection();
	}

	public boolean isParsed(String checksum) throws Exception {
		boolean retval = false;
		init();
		query = "SELECT * FROM AG_T_UPLOAD_LOG WHERE CHECKSUM=?";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, checksum);
		rs = pstmt.executeQuery();
		if (rs.next())
			retval = true;
		return retval;
	}

	public void saveUploadLog(BaseModel model) throws Exception {
		init();
		UploadLog uploadLog = (UploadLog) model;
		query = "INSERT INTO AG_T_UPLOAD_LOG(BATCHID, FILENAME, CHECKSUM, UPLOADEDBY, TMSTMP) VALUES(?, ?, ?, ?, ?)";
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, uploadLog.getBatchID());
		pstmt.setString(2, uploadLog.getFilename());
		pstmt.setString(3, uploadLog.getChecksum());
		pstmt.setString(4, uploadLog.getUploadedBy());
		pstmt.setTimestamp(5, new java.sql.Timestamp(uploadLog.getDtStamp().getTime()));
		pstmt.executeUpdate();
		connection.close();
	}

	public void saveToDb(BaseModel model, String flag, String uuid) throws Exception {
		int i = 0;
		i = update(model, flag, uuid);
		if (i == 0)
			save(model, flag, uuid);
	}

	private int update(BaseModel model, String flag, String uuid) throws Exception {
		int rowAffected = 0;
		init();
		if (flag == "bni") {
			BNIStatement bni = (BNIStatement) model;
			query = "UPDATE AG_T_BANK_STATEMENT SET BATCHID=?, BANKID=?, TXDT=?, JOURNALID=?, DESCRIPTION=?, TXTYPE=?, AMOUNT=?, RECONSTATUS=?, DTSTMP=? WHERE TXDT=? AND JOURNALID=? AND AMOUNT=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, uuid);
			pstmt.setString(2, flag);
			pstmt.setDate(3, new java.sql.Date(bni.getPostDate().getTime()));
			pstmt.setString(4, bni.getJournalNo());
			pstmt.setString(5, bni.getDescription());
			pstmt.setString(6, bni.getDbcr());
			pstmt.setBigDecimal(7, bni.getAmount());
			pstmt.setInt(8, 0);
			pstmt.setTimestamp(9, new java.sql.Timestamp(bni.getDtStamp().getTime()));
			pstmt.setDate(10, new java.sql.Date(bni.getPostDate().getTime()));
			pstmt.setString(11, bni.getJournalNo());
			pstmt.setBigDecimal(12, bni.getAmount());
		} else {
			BMRStatement bmr = (BMRStatement) model;
			String DbCr = "";
			BigDecimal amount = BigDecimal.ZERO;
			if (bmr.getDebit().compareTo(BigDecimal.ZERO) == 0) {
				amount = bmr.getCredit();
				DbCr = "C";
			}
			if (bmr.getCredit().compareTo(BigDecimal.ZERO) == 0) {
				amount = bmr.getDebit();
				DbCr = "D";
			}
			query = "UPDATE AG_T_BANK_STATEMENT SET BATCHID=?, BANKID=?, TXDT=?, JOURNALID=?, DESCRIPTION=?, TXTYPE=?, AMOUNT=?, RECONSTATUS=?, DTSTMP=? WHERE TXDT=? AND JOURNALID=? AND AMOUNT=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, uuid);
			pstmt.setString(2, flag);
			pstmt.setDate(3, new java.sql.Date(bmr.getDateTime().getTime()));
			pstmt.setString(4, bmr.getReferenceNo());
			pstmt.setString(5, bmr.getDescription());
			pstmt.setString(6, DbCr);
			pstmt.setBigDecimal(7, amount);
			pstmt.setInt(8, 0);
			pstmt.setTimestamp(9, new java.sql.Timestamp(bmr.getDtStamp().getTime()));
			pstmt.setDate(10, new java.sql.Date(bmr.getDateTime().getTime()));
			pstmt.setString(11, bmr.getReferenceNo());
			pstmt.setBigDecimal(12, amount);
		}
		rowAffected = pstmt.executeUpdate();
		pstmt.close();
		connection.close();
		return rowAffected;
	}

	private void save(BaseModel model, String flag, String uuid) throws Exception {
		init();
		if (flag == "bni") {
			BNIStatement bni = (BNIStatement) model;
			query = "INSERT INTO AG_T_BANK_STATEMENT(BATCHID, BANKID, TXDT, JOURNALID, DESCRIPTION, TXTYPE, AMOUNT, RECONSTATUS, DTSTMP) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, uuid);
			pstmt.setString(2, flag);
			pstmt.setDate(3, new java.sql.Date(bni.getPostDate().getTime()));
			pstmt.setString(4, bni.getJournalNo());
			pstmt.setString(5, bni.getDescription());
			pstmt.setString(6, bni.getDbcr());
			pstmt.setBigDecimal(7, bni.getAmount());
			pstmt.setInt(8, 0);
			pstmt.setTimestamp(9, new java.sql.Timestamp(bni.getDtStamp().getTime()));
		} else {
			BMRStatement bmr = (BMRStatement) model;
			String DbCr = "";
			BigDecimal amount = BigDecimal.ZERO;
			if (bmr.getDebit().compareTo(BigDecimal.ZERO) == 0) {
				amount = bmr.getCredit();
				DbCr = "C";
			}
			if (bmr.getCredit().compareTo(BigDecimal.ZERO) == 0) {
				amount = bmr.getDebit();
				DbCr = "D";
			}
			query = "INSERT INTO AG_T_BANK_STATEMENT(BATCHID, BANKID, TXDT, JOURNALID, DESCRIPTION, TXTYPE, AMOUNT, RECONSTATUS, DTSTMP) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, uuid);
			pstmt.setString(2, flag);
			pstmt.setDate(3, new java.sql.Date(bmr.getDateTime().getTime()));
			pstmt.setString(4, bmr.getReferenceNo());
			pstmt.setString(5, bmr.getDescription());
			pstmt.setString(6, DbCr);
			pstmt.setBigDecimal(7, amount);
			pstmt.setInt(8, 0);
			pstmt.setTimestamp(9, new java.sql.Timestamp(bmr.getDtStamp().getTime()));
		}
		pstmt.executeUpdate();
		pstmt.close();
		connection.close();
	}

}
