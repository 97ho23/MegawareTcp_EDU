package com.tcpclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

class SocketObject  extends Thread {
	
	private SocketDemon sckDm ;

	private Socket s;
	public DataInputStream dI = null;
	public DataOutputStream dO = null;
	
	String bBodydata="";

	public SocketObject(SocketDemon instance,String ipAddr, int port)  throws Exception {
		
		try {
			s = new Socket(ipAddr, port);
			dI = new DataInputStream(s.getInputStream());
			dO = new DataOutputStream(s.getOutputStream());
			
			JdbcConn jc=null;
		

			//bBodydata=jc.selectData();
			
			
		}
		catch (SocketException soe) {
			System.out.println(new Date() + ": " + "Error SocketObject Create " + soe);
			throw soe;
		}
		catch (IOException ioe)    {
			System.out.println(new Date() + ": " + "Error SocketObject Create " + ioe);			
			throw ioe;
		}
		catch (Exception e) {
			System.out.println(new Date() + ": " + "Error SocketObject Create " + e);
			throw e;
		}
		

	    System.out.println("SocketDemon Start...");

	}
 
	public void run() { // run()
		while ( SocketDemon.bstatus ) {
			try { 

			  String bHeader="100";
			  String bComdata="0063000000";
			  String bBodydata="11111111112222222222";
			  String bTemp="AAAAAAAAAABBBBBBBBBBCCCCCCCCCC";
			  			  
			  String sendMsg ="";
			  //dataVal1=rpad(dataVal1,500,' ',"UTF-8");
			
			  sendMsg=bHeader+bComdata+bBodydata+bTemp;
		  
			  System.out.println("sendMsgLength===="+sendMsg.getBytes("EUC-KR").length);
				
			  //bDataLength=lpad(bDataLength, 4, '0',"UTF-8");//0037
			  
			  dO.write(sendMsg.getBytes("EUC-KR"));			 
						
			System.out.println("########################SentMsg###########################");						
			System.out.println("bHeader		="+bHeader);
			System.out.println("bComdata	="+bComdata);
			System.out.println("bBodydata	="+bBodydata);
			System.out.println("bTemp		="+bTemp);			
			System.out.println("["+sendMsg+"]");
			System.out.println("########################SentMsg###########################");
						
			dO.flush();	
			System.out.println(new Date() + ": " + "2 total message ");
			Thread.sleep(1500);
					
			//�������
			byte[] b_bHeader	 = new byte[3];
			byte[] b_bComdata 	 = new byte[10];
			byte[] b_bBodydata 	 = new byte[20];
			byte[] b_bTemp1	 = new byte[30];
			byte[] b_bTemp2	 = new byte[40];
			
			String s_b_bHeader	 = "";
			String s_b_bComdata	 = "";
			String s_b_bBodydata = "";
			String s_b_bTemp1	 = "";
			String s_b_bTemp2	 = "";
			
			dI.readFully(b_bHeader); 
			dI.readFully(b_bComdata); 
			dI.readFully(b_bBodydata); 
			dI.readFully(b_bTemp1);
			dI.readFully(b_bTemp2);
			
			s_b_bHeader	=  new String(b_bHeader,"EUC-KR");
			s_b_bComdata	=  new String(b_bComdata,"EUC-KR");
			s_b_bBodydata	=  new String(b_bBodydata,"EUC-KR");
			s_b_bTemp1=  new String(b_bTemp1,"EUC-KR");
			s_b_bTemp2=  new String(b_bTemp2,"EUC-KR");
			
			System.out.println("########################ResponseMsg###########################");
			System.out.println("s_b_bHeader	=  ["+s_b_bHeader+"]");
			System.out.println("s_b_bComdata	=  ["+s_b_bComdata+"]");
			System.out.println("s_b_bBodydata	=  ["+s_b_bBodydata+"]");
			System.out.println("s_b_bTemp1	=  ["+s_b_bTemp1+"]");			
			System.out.println("s_b_bTemp2	=  ["+s_b_bTemp2+"]");
			System.out.println("########################ResponseMsg###########################");
			
			}
			catch (Exception e) {
				System.out.println(new Date() + ": " + "Error SocketObject Close " + e);
				close();
			}
		}// while ( sckDm.bstatus )

  }

  public void close() { // run()
	  System.out.println(new Date() + ": " + "Jungea System Fail - Socket Close ");
	  
	 /* try {
		  stmt.executeUpdate("UPDATE TB_FFB_5040NT SET BANK_SYS_STTUS_CD = '3' " + " WHERE (BANK_CD = 'AA' OR BANK_CD = 'CC') AND DELETE_AT = 'N'");
	  }
	  catch (SQLException e) {
		  sckDm.log(new Date() + ": " + "Update Fail AA = '3' " + e.getMessage());
	  }
	  */

	  	if (dI != null)
	  		try {
	  			dI.close();
	  		}
	  		catch (Exception e) {
	  			System.out.println(new Date() + ": " + "Error DataqBase Close " + e.getMessage());
	  		}

	  	if (dO != null)
	  		try {
	  			dO.close();
	  		}
	  		catch (Exception e) {
	  			System.out.println(new Date() + ": " + "Error dO Close " + e.getMessage());
	  		}
	  	
	  	if (s != null)
	  		try {
	  			s.close();
	  		}
	  		catch (Exception e) {
	  			System.out.println(new Date() + ": " + "Error Socket Close " + e.getMessage());
	  		}
	  	
	  	System.exit(0);	
	  }
  
	public String lpad(final Object value, final int length, final char pad, String charset) throws UnsupportedEncodingException
	{
		final StringBuffer result = new StringBuffer();
		
		String strValue = String.valueOf(value);
		
		final int padSize = length - strValue.getBytes(charset).length;
		
		if(padSize < 0)
		{
			System.out.println("Input String is Bigger Then Length" + strValue.getBytes().length + ">" + length);
		}
		
		for(int idx=0; idx<padSize; ++idx)
		{
			result.append(pad);
		}
		result.append(strValue);
		
		return result.toString();
		
	}
	
	public static String rpad(final Object value, final int length, final char pad, String charset) throws UnsupportedEncodingException
	{
		final StringBuffer result = new StringBuffer();
		
		String strValue = String.valueOf(value);
		
		final int padSize = length - strValue.getBytes(charset).length;
		
		result.append(strValue);
		
		
		
		for(int idx=0; idx<padSize; ++idx)
		{
			result.append(pad);		
			
		}
		
		return result.toString();
		
	}
	
  

}  //SocketObject {
