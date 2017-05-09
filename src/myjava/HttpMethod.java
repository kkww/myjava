package myjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpMethod extends HttpStatus {
	private HttpStatus httpStatus;
	private String method;
	private BufferedReader br;
	
	public HttpMethod(HttpStatus httpStatus, String mtd, BufferedReader br) {
		this.httpStatus = httpStatus;
		method = mtd;
		this.br = br;
	}
	
	public void responseGET(String mtd,
							OutputStream outputStream,
							File file)
							throws IOException {
		consolLog( method + " Method");

		Path path = file.toPath();
		String mimeType = Files.probeContentType( path );

		consolLog("path : " + path);
		consolLog("mimeType : " + mimeType);
		
		byte[] body = Files.readAllBytes( path );
				
		httpStatus.response200(outputStream, mimeType);
		outputStream.write( body );
	}

	public void responsePOST(String mtd,
							OutputStream outputStream,
							File file,
							int contentLength) //body 길이
							throws IOException {
		consolLog( method + " Method");
		consolLog("Content-Length : " + contentLength);
		
		Path path = file.toPath();
		String mimeType = Files.probeContentType( path );
		
		consolLog("path : " + path);
		consolLog("mimeType : " + mimeType);

		char[] temp = new char[contentLength];
		br.read(temp, 0, contentLength);
		String body = String.valueOf(temp);
		
		consolLog("POST method body : " + body);
		
		String location = null;
		String[] lineTokens;
		String[] valueTokens;
		String email = null, pw = null;
		if(path.endsWith("join.html") || path.endsWith("login.html")) {
			location = "/index.html";
			lineTokens = body.split("&");
			System.out.println("line Tokens = " + lineTokens);
			for(int i = 0; i < lineTokens.length; i++) {
				valueTokens = lineTokens[i].split("=");
				if(valueTokens[0].equals("email")) email = valueTokens[1];
				if(valueTokens[0].equals("password")) pw = valueTokens[1];
			}
			Member member = new Member(email, pw);
			consolLog("email:" + member.getEmail() + ",pw:" + member.getPassword());
			
			if(path.endsWith("join.html")) {
				member.join_DB(email, pw);
				httpStatus.response302(outputStream, location);
			} else if(path.endsWith("login.html")) {
				boolean loginResult = member.login_DB(email, pw);
				if(loginResult == false) {
					consolLog("login result is false");
					httpStatus.response302(outputStream, location, "false");
				} else {
					consolLog("login result is true");
					httpStatus.response302(outputStream, location, "true");
				}
			}
		}
		outputStream.write( body.getBytes() );
	}
	
	private void consolLog(String str) {
		System.out.println("[HttpMethod] " + str);
	}
}
