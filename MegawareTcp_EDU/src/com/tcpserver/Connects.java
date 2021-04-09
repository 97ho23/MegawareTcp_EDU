package com.tcpserver;
 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

class Connects extends Thread {
	
	BankServer bserver ;
	
	Socket sc;
	DataInputStream in = null;
	DataOutputStream out = null;

	InetAddress host = null;
	int socketnum;
	
	static int tot = 0;
	
	String bBodydata="";
	
	public synchronized void getTot(){
		System.out.println( "TOt=>"+ (++tot));
	}

	public Connects(BankServer bsrv, Socket s,int i ) {
		
		bserver = bsrv;
		sc = s;
		socketnum = i;
		
		try {
			in = new DataInputStream(sc.getInputStream());
			
		
			System.out.println(": ************ Connects in => " + in);
			
			//JdbcConn jc=null;
			

			//bBodydata=jc.selectData();
			
			
			
			out = new DataOutputStream(sc.getOutputStream());
			
		}  catch (IOException e) {
			try {
				System.out.println(new Date() + ": IO Exception - " + e);
				sc.close();
			} catch (IOException ex) {
				System.out.println(new Date() + ": IO Exception - " + ex);
			}
			return;
		}
	
		this.start(); // Thread start here !!
	}

	public void run() { // run()
 
		host = sc.getInetAddress(); 
		String ip = host.getHostAddress();
		System.out.print("\nClient IP : " + ip);
		System.out.println(" Port # :" + sc.getPort());
		
			byte[] bheader= new byte[4];
			byte[] bcomdata = new byte[20];                
			byte[] bbodydata = new byte[30];                
			byte[] btemp = new byte[40];                
				
		 
		String sendmsg  = "";
		
		try {
			while (true) 
			{
				
				in.readFully(bheader);
				in.readFully(bcomdata);
				in.readFully(bbodydata);
				in.readFully(btemp);
				
				String r_bheader=  new String(bheader,"euc-kr");
				String r_bcomdata=  new String(bcomdata,"euc-kr");
				String r_bbodydata=  new String(bbodydata,"euc-kr");
				String r_btemp=  new String(btemp,"euc-kr");
				
				System.out.println("##########요청#################");
				System.out.println("r_bheader	=["+r_bheader+"]");
				System.out.println("r_bcomdata	=["+r_bcomdata+"]");
				System.out.println("r_bbodydata	=["+r_bbodydata+"]");
				System.out.println("r_btemp		=["+r_btemp+"]");
								
				int regMsgLeng=0;
				
				regMsgLeng=regMsgLeng+r_bheader.getBytes().length;
				regMsgLeng=regMsgLeng+r_bcomdata.getBytes().length;
				regMsgLeng=regMsgLeng+r_bbodydata.getBytes().length;
				regMsgLeng=regMsgLeng+r_btemp.getBytes().length;
				
				System.out.println("길이 regMsgLeng="+regMsgLeng);
				
				System.out.println("##########요청#################");
				
				
				
				/*
				sendmsg=sendmsg+r_bheader;
				sendmsg=sendmsg+r_bcomdata;
				sendmsg=sendmsg+r_bbodydata;
				sendmsg=sendmsg+r_btemp;
				*/
				
				sendmsg=sendmsg+"0094";
				//sendmsg=sendmsg+"1111";
				sendmsg=sendmsg+"AAAAAAAAAAAAAAAAAAAA";
				sendmsg=sendmsg+"BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
				sendmsg=sendmsg+"CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";
				
				System.out.println("sendmsg="+sendmsg);
				System.out.println("sendmsgLength="+sendmsg.getBytes().length);
				
				out.write(sendmsg.getBytes("euc-kr"));
				out.flush();
				
			} //  while (true)
		} catch (Exception e) {
			System.out.println(new Date() + ": " + " Exception Socket " + socketnum +" Close : " + e);
		} finally {
			try {

				if (sc != null) {
					System.out.println(new Date() + ": " + " sc Close");
					sc.close();
				}
				
				if (in != null) {
					System.out.println(new Date() + ": " + " in Close");
					in.close();
				}
				
				if(out!=null){
					System.out.println(new Date() + ": " + " out Close");
					out.close();
				}
				
				System.out.println(new Date() + ": " + " finally Socket " + socketnum + " socket Close : ");

				//System.out.println(new Date() + ": " + " Socket " + socketnum + " DB Close : ");
				bserver.vCnts.removeElement(this);
				System.out.println(new Date() + ": " + " Connects " + socketnum + "  Instance Close : ");
			}
			catch (IOException e) {
				System.out.println(new Date() + ": " + " Socket " + socketnum + " Close : " + e);
			}
		} //try finally
	} //  End of run
	
	public static String rpad(final Object value, final int length, final char pad)
	{
		final StringBuffer result = new StringBuffer();
		
		String strValue = String.valueOf(value);
		
		final int padSize = length - strValue.getBytes().length;
		
		result.append(strValue);
		for(int idx=0; idx<padSize; ++idx)
		{
			result.append(pad);
		}
		
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
  
	
} // End of Connect  Class