package myjava;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {
	private final static int PORT = 4000;
	
	public static void consolLog(String str) {
		System.out.println("[HttpServer] " + str);
	}
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			//1.Server socket
			serverSocket = new ServerSocket();
			
			//2.Binding(socket address = IP address + port)
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhost = inetAddress.getHostAddress();
			
			serverSocket.bind(
					new InetSocketAddress(localhost, PORT)); //InetSocket : IP와 Port로 binding
			consolLog("bind: " + localhost + ":" + PORT);
			
			while(true) {
				//3.Accept(연결 요청 기다림)
				Socket socket = serverSocket.accept();
				
				Thread thread = new Thread( new RequestHandler(socket) );
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket.isClosed() == false && serverSocket != null)
					serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
