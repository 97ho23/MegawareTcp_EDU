package com.tcpclient;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class SocketDemon {
	
  static public SocketDemon instance; // The single instance
  static public boolean bstatus ;

  static String url;
  static int port ;
  static String DBURL ;
  static String DBUSERID ;
  static String DBPASSWORD ;
  static String DBDriver ;
  
  static PrintWriter log;
  
  private SocketDemon()  throws Exception {
    try {
      init();
    }
    catch (Exception e) {
      throw e;
    }
    bstatus = true;
  }

  private void init()  throws Exception {
    InputStream is = null;
    is = getClass().getResourceAsStream("SocketDemon.properties");
    Properties socketProps = new Properties();
    try {
      socketProps.load(is);
      url = socketProps.getProperty("URL");
      port = Integer.parseInt(socketProps.getProperty("PORT", "0"));
      DBURL = socketProps.getProperty("DBURL");
      DBUSERID = socketProps.getProperty("DBUSERID");
      DBPASSWORD = socketProps.getProperty("DBPASSWORD");
      DBDriver = socketProps.getProperty("DBDriver");
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
    	System.out.println("Can't read the properties file. Make sure BankServer.properties is in the CLASSPATH");
    }
    
    SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
   
	String logFile = socketProps.getProperty("logfile", "/data/SocketDemon_"+formater.format(new Date())+".log");
	
        if(is != null)
        {
            is.close();
        }   

  }

   public void log(String msg) {
	   System.out.println(new Date() + ": " + msg);
   }

   public void log(Throwable e, String msg) {
	   System.out.println(new Date() + ": " + msg);
     e.printStackTrace(log);
   }

  public static void main(String[] argv) throws IOException {

    SocketObject BankSocket;

    try {
      instance = new SocketDemon();
      
      System.out.println("instance="+instance);
      System.out.println("url="+url);
      System.out.println("port="+port);
      
      BankSocket = new SocketObject( instance, url , port);
      BankSocket.start();
 
    } catch (Exception e) {
    	System.out.println(new Date() + ": Main Err : " + e );
    }
  
  }

} // SocketDemon Class close
