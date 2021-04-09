package com.tcpserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpIpServer implements Runnable {
	ServerSocket serverSocket;
	Thread[] threadArr;

	public static void main(String[] args) {
		// 5개의 ?��?��?���? ?��?��?��?�� ?��버�?? ?��?��?��?��.
		TcpIpServer server = new TcpIpServer(5);
		server.start();
	}

	public TcpIpServer(int num) {
		try {
			// ?���? ?��켓을 ?��?��?��?�� 7777�? ?��?��?? 바인?��.
			serverSocket = new ServerSocket(8701);
			System.out.println(getTime() + " ?��버�? �?비되?��?��?��?��.");

			threadArr = new Thread[num];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		for (int i = 0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}
	}

	public void run() {
		
		while (true) {
			try {
				System.out.println(getTime() + " �? ?���? ?���??�� 기다립니?��.");

				Socket socket = serverSocket.accept();
				System.out.println(getTime() + " " + socket.getInetAddress() + "로�??�� ?��결요�??�� ?��?��?��?��?��?��.");
				
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				
				int value=0;
				// reads to the end of the stream 
		         while((value = inFromClient.read()) != -1)
		         {
		            // converts int to character
		            char c = (char)value;
		            
		            // prints character
		            System.out.print(c);
		         }
		         

				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);


				dos.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static String getTime() {
		String name = Thread.currentThread().getName();
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date()) + name;
	}
}