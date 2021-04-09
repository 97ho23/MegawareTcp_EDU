package com.tcpclient;

import java.io.UnsupportedEncodingException;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 


	public class JdbcConn { 

	public static String selectData() { 
	String driver = "oracle.jdbc.OracleDriver"; 
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl"; 
	
	String user = "skyomg7"; 
	String pw = "omg77"; 	

	String sql = "SELECT TCP_DATA FROM TCP_TEST_DATA";	
	sql=sql+" WHERE SEQ=2";
	 
	String retStr="";

	Connection con = null; 
	Statement st = null; 
	ResultSet rs = null; 

	System.out.println("main start"); 
	try { 

	Class.forName (driver); 	 
	
	con = DriverManager.getConnection(url,user,pw); 
	
	st = con.createStatement(); 
	
	rs = st.executeQuery(sql); // select 구문 처리 
	
	while(rs.next()) { 
	
	System.out.print(rs.getString("TCP_DATA")+"\t"); 
	retStr=rs.getString("TCP_DATA");	

	System.out.println("TCP_DATA length()==="+retStr.length());
	try {
		System.out.println("TCP_DATA(UTF-8) length()==="+retStr.getBytes("UTF-8").length);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	} 



	} catch(ClassNotFoundException ex) { 
	
	} catch(SQLException ex){ 
		System.out.println(ex.getMessage()); 
	
	} finally 
	{ 
			try { 
			if(st !=null)st.close();  
			if(rs != null) rs.close(); 
			if(con != null) con.close(); 
			
			} catch(SQLException ex) { 
			System.out.println(ex.getMessage()); 
			} 
	} 
		return retStr;
	} 

	}



