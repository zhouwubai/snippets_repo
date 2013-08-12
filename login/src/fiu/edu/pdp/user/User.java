package fiu.edu.pdp.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import fiu.edu.pdp.db.DBUtil;

public class User {

	private String user_name;
	private String nick_name;
	private String email;
	private String password;
	
	private static final String CURRENT_USER_ATTRIBUTE = "currentUser";	
	private static String USER_QUERY = "select user_name,nick_name,password,email from user where email = ? and password = ?";
	
	public static boolean userValidate(String username,String password, HttpSession session){
		
	Connection conn = null;
	PreparedStatement pstm = null;
	
	try {
		conn = DBUtil.getConnection();
		pstm = conn.prepareStatement(USER_QUERY);
		pstm.setString(1, username);
		pstm.setString(2, password);
		
		ResultSet rs = pstm.executeQuery();
		if(rs.next()){
			String rtPassword = rs.getString("password");
			String rtUsername = rs.getString("user_name");
			String email = rs.getString("email");
			String nick_name = rs.getString("nick_name");
			
//			if(session.getAttribute(CURRENT_USER_ATTRIBUTE) == null){
//				session.setAttribute(CURRENT_USER_ATTRIBUTE, new ArrayList<User>());
//			}
//			
//			ArrayList<User> users = (ArrayList<User>)session.getAttribute(CURRENT_USER_ATTRIBUTE);
			User user = new User(rtUsername, nick_name, email, rtPassword);
			session.setAttribute(CURRENT_USER_ATTRIBUTE, user);
			return true;
		}else{
			return false;
		}		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return false;
	}finally{
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	}
	
	
	
	public static void removeFromSession(HttpSession session) {
		if (session.getAttribute(CURRENT_USER_ATTRIBUTE) != null) {
			session.removeAttribute(CURRENT_USER_ATTRIBUTE);
		}
	}

	public static User getFromSession(HttpSession session) {
		if (session.getAttribute(CURRENT_USER_ATTRIBUTE) == null) {
			return null;
		} else {
			return (User)session.getAttribute(CURRENT_USER_ATTRIBUTE);
		}
	}
	
	
	

	public User(String user_name,String nick_name, String email, String password) {
		// TODO Auto-generated constructor stub
		this.user_name = user_name;
		this.nick_name = nick_name;
		this.email = email;
		this.password = password;
	}
	
	public User(String user_name){
		this.user_name = user_name;
	}
	
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
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
	
	
//	@Override
//	public boolean equals(Object obj) {
//		// TODO Auto-generated method stub
//		User user = (User) obj;
//		return this.user_name.equals(user.user_name);
//	}
	
	
	
	public static void main(String[] args) {
		
		List<User> list = new ArrayList<User>();
		User user1 = new User("zhouwubai");
		list.add(user1);
		
		User user2 = new User("zhouwubai", "wubai", "wubai", "abc");
		
		System.out.println(user1.equals(user2));
		System.out.println(list.indexOf(user2));
		
	}
	
	
}
