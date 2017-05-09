package myjava;

import java.sql.*;

//JDBC 사용 방법
//1. JDBC 드라이버 로드 : Class.forName(oracle.jdbc.driver.OracleDriver);
//2. DB Server 연결 : DriverManager.getConnection(jdbc:oracle:thin:@localhost:XE, scott, tiger)
//3. SQL 쿼리문 명령 : Statement 또는 PreparedStatement
//4. 결과 처리 : executeQuery(SELECT 일 때), executeUpdate(UPDATE, INSERT, DELETE)
//5. 연결 종료 :

//연결 순서 : Connection > Statement 또는 PreparedStatement > ResultSet [ 단, ResultSet 은 SELECT 일때만 사용한다 ]
//연결 종료 순서 : ResultSet > Statement 또는 PreparedStatement > Connection [ 단, ResultSet 은 SELECT 일때만 사용한다 ]

//출처: http://gangzzang.tistory.com/entry/Java-이클립스Eclipse-JDBC-오라클ORACLE-개발-환경-설정 [갱짱.study]


/*
jdbc:oracle:driver_type:[username/password]@[//]host_name[:port][/XE]

위의 프로토타입으로부터 아래 정보를 해석해 보면 다음과 같다.

jdbc:oracle:thin:@localhost:1521:xe

-. jdbc:oracle:thin은 사용하는 JDBC드라이버가 thin 타입을 의미한다. 자바용 오라클 JDBC드라이버는 크게 두가지가 있는데 하나는 Java JDBC THIN 드라이버고, 다른 하나는 OCI기반의 드라이버라고 한다. 

-. username/password은 option이다. [ ]안에 있는 정보는 반드시 명기할 필요는 없다는 뜻이다.

-. :port 번호도 option이다. 다만 Oracle의 listener port인 1521을 사용하지 않을 경우는 이 값을 명기해 줘야 된다. 예를 들어서 jdbc:oracle:thin:hr/hr@//localhost:1522

-. localhost는 Oracle DB가 설치되어 있는 서버의 IP인데 위 경우는 로컬에 설치되어 있다는 뜻이다.

-. 1521 은 오라클 listener의 포트번호이다.

-. /XE는 Oracle database client의 고유한 service name이다. 디폴트로 XE를 사용하므로 이 정보도 option이다. 이에 대한 설정 정보는 Oracle이 설치된 폴더 아래의 app\oracle\product\11.2.0\server\network\ADMIN\listener.ora 파일에 다음과 같이 표시되어 있다.

DEFAULT_SERVICE_LISTENER = (XE)

출처: http://developer-joe.tistory.com/82 [코드 조각-Android, Java, C#, C, C++, JavaScript, PHP, HTML, CSS, Delphi]
*/

public class DBConnection {
	protected static Connection dbConn;
    
    protected static Connection getConnection()
    {
        Connection conn = null;
        try {
            String user = "scott"; 
            String pw = "tiger";
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            
            Class.forName("oracle.jdbc.driver.OracleDriver");        
            conn = DriverManager.getConnection(url, user, pw);
            
            System.out.println("Database에 연결되었습니다.\n");
            
        } catch (ClassNotFoundException cnfe) {
        	consolLog("DB 드라이버 로딩 실패 :"+cnfe.toString());
        } catch (SQLException sqle) {
        	consolLog("DB 접속실패 : "+sqle.toString());
        } catch (Exception e) {
        	consolLog("Unkonwn error");
            e.printStackTrace();
        }
        return conn;     
    }
    
    private static void consolLog(String str) {
		System.out.println("[DBConnection] " + str);
	}
}
