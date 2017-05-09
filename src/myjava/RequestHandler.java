package myjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
	BufferedReader br = null;
	Socket socket = null;
	OutputStream outputStream = null;
	HttpStatus httpStatus = null;
	
	public RequestHandler (Socket socket) {
		this.socket = socket;
	}
	
	public static void consolLog(String str) {
		System.out.println("[RequestHandler] " + str);
	}
	
	@Override
	public void run() {
		try {
			InputStreamReader isr = //byte->charecter
					new InputStreamReader(socket.getInputStream(), "utf-8");
			br = new BufferedReader(isr);
			
			httpStatus = new HttpStatus();
			outputStream = socket.getOutputStream();
			
			Map<String, String> headerFields = new HashMap<String, String>();
			System.out.println("-------------------------------------------------");

			//http protocol header - method
			String request = br.readLine();
			String[] tokens = request.split(" ");
			String mtd = tokens[0];
			String url = tokens[1];
			String protocol = tokens[2];
			consolLog(mtd + " " + url + " " + protocol);
			
			String line = null; //line read 용 변수
			while((line = br.readLine()) != null) {
				if(line == null || "".equals(line)) { //읽은게 없으면
					break;
				}
				
				consolLog(line); //Accept: text/html, application/xhtml+xml, image/jxr, */*
				
				if(line.contains(": ")) {
					tokens = line.split(": ", 2); //split limited 2 tokens
					
					if(tokens.length == 2) {
						headerFields.put(tokens[0], tokens[1]);
					}
					
				}
			}
			
			System.out.println("-------------------------------------------------");

			if(mtd.equals("GET")) { //GET
				responseStaticResource(mtd, outputStream, url, 0);
			} else if(mtd.equals("POST")) {
				if(headerFields.get("Content-Length") != null) {
					int contentLength = Integer.parseInt(headerFields.get("Content-Length"));
					responseStaticResource(mtd, outputStream, url, contentLength);
				}
			} else { //PUT, DELETE, ...
				httpStatus.response400Error(outputStream);
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
	
	//response method
	private void responseStaticResource(String mtd,
										OutputStream outputStream,
										String url,
										int contentLength)
										throws IOException {
		consolLog("responseStaticResource method check : " + mtd);
		if(url.equals("/")) {
			url = "/index.html";
		}
		
		File file = new File( "./webapp" + url );
		
		if ( file.exists() == false ) {
			httpStatus.response404Error( outputStream );
		     return;
		}
		
		HttpMethod method = new HttpMethod(httpStatus, mtd, br);
		if(mtd.equals("GET")) method.responseGET(mtd, outputStream, file);
		else if(mtd.equals("POST")) method.responsePOST(mtd, outputStream, file, contentLength);
	}
	
	

}