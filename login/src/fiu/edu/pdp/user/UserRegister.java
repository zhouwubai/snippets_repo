package fiu.edu.pdp.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fiu.edu.pdp.db.DBUtil;

public class UserRegister {
	
	public static String CHK_EMAIL_SQL = "select email from user where email = ?";
	public static String INSERT_USER_SQL = "insert into user (email,nick_name,password,type) VALUES " +
							" (?,?,?,1)";
	

	public static boolean checkEmailExist(String email){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(CHK_EMAIL_SQL);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}finally{
				try {
					if(conn != null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	
	public static boolean registerUser(
			String email, String nickName, String password){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(INSERT_USER_SQL);
			stmt.setString(1, email);
			stmt.setString(2, nickName);
			stmt.setString(3, password);
			
			int rs = stmt.executeUpdate();
			
			if(rs != 0){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}finally{
				try {
					if(conn != null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	
	public static void main(String[] args) {
		
		boolean check = UserRegister.checkEmailExist("zhoudwubai@gmail.com");
		System.out.println(check);
		check = UserRegister.registerUser("abc", "abc", "abc");
		System.out.println(check);
	}
}
