package com.agit.tcash.recon.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ReconDao {
	private DataSource dataSource = null;
	private Connection connection = null;
	private CallableStatement pstmt = null;
	private String query = "";

	private void init() throws SQLException, NamingException {
		Context ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/tsel_tunai");
		// dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/local");
		connection = dataSource.getConnection();
	}

	public boolean doRecon(String batch) {
		boolean retval = false;
		try {
			init();
			query = "call pkg_ag_recon_rk.doRecon(?)";
			pstmt = connection.prepareCall(query);
			pstmt.setString(1, batch);
			pstmt.execute();
			retval = true;
		} catch (Exception e) {
			e.printStackTrace();
			retval = false;
		}finally {
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retval;
	}
}
