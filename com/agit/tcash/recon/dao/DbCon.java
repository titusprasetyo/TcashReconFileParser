package com.agit.tcash.recon.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import oracle.jdbc.driver.OracleDriver;

public class DbCon
{
  public static Connection getConnection()
    throws Exception
  {
    Connection con = null;
    Context ctx = new InitialContext();
    
    DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/tsel_tunai");
    
    con = ds.getConnection();
    
    return con;
  }
  
  public static Connection createDBConnection(String url)
    throws Exception
  {
    Class.forName("org.gjt.mm.mysql.Driver");
    Connection con = DriverManager.getConnection(url);
    return con;
  }
  
  public static Connection createDBConnectionOra(String url)
    throws Exception
  {
    DriverManager.registerDriver(new OracleDriver());
    Connection con = DriverManager.getConnection(url);
    return con;
  }
  
  public static void closeConnection(Connection con)
  {
    try
    {
      con.close();
    }
    catch (Exception e)
    {
      e.printStackTrace(System.out);
    }
  }
}
