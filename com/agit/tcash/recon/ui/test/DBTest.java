package com.agit.tcash.recon.ui.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Messagebox;

public class DBTest extends SelectorComposer<Component> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataSource dataSource = null;
	private Connection connection = null;
	private PreparedStatement pstmt = null;
	private String result = "";
	private ResultSet rs = null;
	@Listen("onClick = #btnTest")
	public void upload() {
		Context ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/local");
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("SELECT * FROM AG_T_UPLOAD_LOG");
			rs = pstmt.executeQuery();
			while (rs.next()){
				result += rs.getString(1) + "\n";
			}
			Messagebox.show("Result : \n" + result);
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Messagebox.show("Error : " + e.getMessage());
		}
	}
}
