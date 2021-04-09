package com.tcpserver;


import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

public class BankServer {

	protected ServerSocket checkServer = null;
	protected Vector<Connects> vCnts;
	int iport = 0;
	int socketcount = 0;

	protected PrintWriter log;

	protected String DBURL ;
	protected String DBUSERID ;
	protected String DBPASSWORD ;
	protected String DBDriver ;

	public BankServer() {

		int i = 0;
		boolean work = true;

		init();
		if (iport == 0 || socketcount == 0 ) {
			log("Init Error");
			System.out.println("Init Error ");
			System.exit(0);
		}

		try {
			checkServer = new ServerSocket(iport);
			
			log("BankServer Start ....");
			System.out.println("BankServer Start ....");
			log(" Port :" + iport + " Total socket : " + socketcount );
		}
		catch (IOException ex) {
			log(ex,"Exception ...");
			System.out.println("Init Error ");
			System.exit(0);
		}

		vCnts = new Vector<Connects>(socketcount);

		try {
			while (work) {

				Socket client = checkServer.accept();
				Connects cnts = new Connects(this, client, i);
				vCnts.addElement(cnts);
				i = i + 1 ;
				if (i >= socketcount) {  i = 0 ;    }
			} //while
		}
		catch (IOException e) {
			log("Exception ..." + e);			
		}

		log("Bank Server Close.....");
	} 


	private void init() {
		InputStream is = getClass().getResourceAsStream("BankServer.properties");
		Properties serverProps = new Properties();

		try {
			serverProps.load(is);
			iport = Integer.parseInt(serverProps.getProperty("port", "0"));
			socketcount = Integer.parseInt(serverProps.getProperty("socketcount", "0"));
			DBURL = serverProps.getProperty("DBURL");
			DBUSERID = serverProps.getProperty("DBUSERID");
			DBPASSWORD = serverProps.getProperty("DBPASSWORD");
			DBDriver = serverProps.getProperty("DBDriver");
			
			System.out.println("iport="+iport);
			System.out.println("socketcount="+socketcount);
			System.out.println("DBURL="+DBURL);
			System.out.println("DBUSERID="+DBUSERID);
			System.out.println("DBPASSWORD="+DBPASSWORD);
			System.out.println("DBDriver="+DBDriver);
			
			System.out.println("comdata:	10");
			System.out.println("element:	2");
			System.out.println("bodydata:	5");
			System.out.println("space:		3");
			
			
			/**
		} catch (java.rmi.RemoteException e) {
		      System.err.println("Can't read the properties file. " +
		                         "Make sure BankServer.properties is in the CLASSPATH");
		} catch (javax.ejb.EJBException e) {
		      System.err.println("Can't read the properties file. " +
		                         "Make sure BankServer.properties is in the CLASSPATH");
		} catch (java.io.IOException e) {
		      System.err.println("Can't read the properties file. " +
		                         "Make sure BankServer.properties is in the CLASSPATH");
		}
			 */
		}
		catch (Exception e) {
			log("Can't read the properties file. Make sure BankServer.properties is in the CLASSPATH");
		}
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		
		//String logFile = serverProps.getProperty("logfile", "d:/temp/BankServer_"+formater.format(new Date())+".log"); //?�영
		//String logFile = serverProps.getProperty("logfile", "/data/logs/banking/DEV/bnk/BankServer_"+formater.format(new Date())+".log");
		String logFile = serverProps.getProperty("logfile", "/home/megaware/MWT/site_Test/MDM/bank/logs/BankServer_"+formater.format(new Date())+".log");
		
		try {
			log = new PrintWriter(new FileWriter(logFile, true), true);
		}
		catch (IOException e) {
			System.err.println("Can't open the log file: " + logFile);
			log = new PrintWriter(System.err);
		}
		finally
		{
			if(is != null)
			{
				try
				{
					is.close();
				}
				catch(Exception e)
				{
					//aeis.common.base.util.AjaxComUtil.logHelperToErr(e);
					//System.out.println("Exception ..." + e);
					log("Exception ..." + e);
				}
			}
		}
	}

	public void log(String msg) {
		log.println(new Date() + ": " + msg);
	}

	public void log(Throwable e, String msg) {
		log.println(new Date() + ": " + msg);
		e.printStackTrace(log);
	}

	public static void main(String[] argv) throws IOException {
		new BankServer();
	}

}  // End of BankServer Class
