package myjava;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestHandler implements Runnable {
	BufferedReader br = null;
	Socket socket = null;
	OutputStream outputStream = null;
	
	public RequestHandler (Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			InputStreamReader isr = //byte->charecter
					new InputStreamReader(socket.getInputStream(), "utf-8");
			br = new BufferedReader(isr);
			
			String request = null;
			String line = null;
			while((line = br.readLine()) != null) {
				if(line == null || "".equals(line)) { //읽은게 없으면
					break;
				}
				if(request == null) { //첫줄
					request = line;
				}
			}
			
			String url = null;
			String protocol = null;
			String[] tokens = request.split(" ");
			if("GET".equals(tokens[0])) { //GET
				responseStaticResource(outputStream, url, protocol);
			} else { //POST
				//
			}
					
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//GET
	private void responseStaticResource(OutputStream outputStream,
										String url,
										String protocol )
										throws IOException {
		File file = new File( "./webapp" + url );
		Path path = file.toPath();
		byte[] body = Files.readAllBytes( path );
		
	}
	

/*
	outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" )
	outputStream.write( "Content-Type:text/html\r\n".getBytes( "UTF-8" ) );
	outputStream.write( "\r\n".getBytes() );
	outputStream.write( body );
*/
}
