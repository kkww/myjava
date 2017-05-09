package myjava;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpStatus {
	private Map<Integer, String> status = new HashMap<Integer, String>();
	private String location;
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	HttpStatus() {
		status.put(200, "OK");
		status.put(301, "Moved Permanentl");
		status.put(400, "Bad Request");
		status.put(404, "File Not Found");
	}
	
	//success
	public void response200(OutputStream outputStream, String mimeType) throws IOException {
		outputStream.write( ( "HTTP/1.1 200 OK\r\n").getBytes( "UTF-8" ) );
		outputStream.write( ("Content-Type:" + mimeType +"\r\n").getBytes( "UTF-8" ) );
		outputStream.write( "\r\n".getBytes() ); 
	}
	
	public void response302(OutputStream outputStream, String location) throws IOException {
		outputStream.write( ( "HTTP/1.1 302 Found\r\n").getBytes( "UTF-8" ) );
		outputStream.write( ("Location: " + location + "\r\n").getBytes( "UTF-8" ) );
		outputStream.write( "\r\n".getBytes() ); 
	}
	
	public void response302(OutputStream outputStream, String location, String cookie) throws IOException {
		outputStream.write( ( "HTTP/1.1 302 Found\r\n").getBytes( "UTF-8" ) );
		outputStream.write( ("Location: " + location + "\r\n").getBytes( "UTF-8" ) );
		outputStream.write( ( "Set-Cookie: logined=" + cookie + "\r\n").getBytes( "UTF-8" ) );
		outputStream.write( "\r\n".getBytes() ); 
	}
	
	//error
	public void response400Error(OutputStream outputStream) throws IOException {
		byte[] body = Files.readAllBytes(new File("./webapp/error/404.html").toPath());
		outputStream.write( ( "HTTP/1.1 400 Bad Request\r\n" ).getBytes() );
		outputStream.write( "Content-Type:text/html\r\n".getBytes() );
		outputStream.write( "\r\n".getBytes() ); 
		outputStream.write( body );
	}
	
	public void response404Error(OutputStream outputStream) throws IOException {
		byte[] body = Files.readAllBytes(new File("./webapp/error/400.html").toPath());
		outputStream.write( ( "HTTP/1.1 404 File Not Found\r\n").getBytes() );
		outputStream.write( "Content-Type:text/html\r\n".getBytes() );
		outputStream.write( "\r\n".getBytes() ); 
		outputStream.write( body );
	}
	
	private void consolLog(String str) {
		System.out.println("[HttpStatus] " + str);
	}
}
