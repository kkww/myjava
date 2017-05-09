package myjava;

public class Member {
	private String email;
	private String password;
	
	Member() {}
	
	public Member(String email, String password) {
		this.email = email;
		this.password = password;
		consolLog("Member create : " + email + ", " + password);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void join_DB (String email, String password) {
		memberInfo_DB("INSERT", email, password);
	}
	
	public boolean login_DB (String email, String password) {
		memberInfo_DB("SEARCH_MEM", email, password);
		return DBQuery.result;
	}
	
	private void memberInfo_DB (String queryType, String email, String password) {
		consolLog(queryType + ", " + email + ", " + password);
		DBQuery db = new DBQuery(queryType, email, password);
		db.executeDB();
	}
	
	private void consolLog(String str) {
		System.out.println("[Member] " + str);
	}
}
