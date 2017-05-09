package myjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQuery extends DBConnection {
	private static String queryType = null;
	private static String email = null;
	private static String password = null;
	public static boolean result;
	
	DBQuery(String queryType, String email, String password) {
		this.queryType = queryType;
		this.email = email;
		this.password = password;
	}
	
	public static boolean resultDB(String p, String e) {
		if(e != null && p != null) {
			consolLog("true");
			consolLog("e:" + e + "/p:" + p + ", email:" + email + "/password:" + password);
			if( email.equals(p) ) {
					return true;
			}
			else {
				return false;				
			}
		} else {
			consolLog("false");
			consolLog("e.equals(email):" + e.equals(email) + ", p.equals(password):" + p.equals(password));
			return false;
		}
	}
	
    public static void executeDB() {
		Connection conn = null; // DB연결된 상태(세션)을 담은 객체
		PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
		ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체
	
		try {// SQL 문장을 만들고 만약 문장이 질의어(SELECT문)라면, 그 결과를 담을 ResulSet 객체를 준비한 후 실행시킨다.
			String query = null;
			
			switch(queryType) {
			case "INSERT" :
				query = "INSERT INTO MEMBER VALUES(\'" + email + "\'" + ", " + "\'" + password + "\')";
				break;
			case "SEARCH_MEM" :
				query = "SELECT EMAIL, PW FROM MEMBER WHERE EMAIL=\'" + email + "\' and PW=\'" + password + "\'";
				break;
			}
			
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(query);
			rs = pstm.executeQuery();
			
			if(queryType == "SEARCH_MEM") {
				while(rs.next()) {
					String e = rs.getString("EMAIL");
					String p = rs.getString("PW");
					result = resultDB(e, p);
				}
			}
		} catch (SQLException sqle) {
			consolLog("sql error : " + sqle.toString());
			sqle.printStackTrace();
		} finally {
		    // DB 연결을 종료한다.
			try {
				if ( rs != null ) {rs.close();}   
				if ( pstm != null ) {pstm.close();}   
				if ( conn != null ) {conn.close(); }
			} catch(Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
    }
    
    private static void consolLog(String str) {
		System.out.println("[DBQuery] " + str);
	}
}
