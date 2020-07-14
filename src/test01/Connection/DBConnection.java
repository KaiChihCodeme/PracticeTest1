package test01.Connection;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//�]�m��Ʈw�s��
public class DBConnection {
	static Connection connecter = null;
	static ResultSet rs = null;
	static PreparedStatement ps = null;
	
	public static ResultSet getResult() {
		return rs;
	}
	
	public static PreparedStatement getPreparedStatement() {
		return ps;
	}

	public static Connection getConnect() {
		// �]�w��Ʈwurl
		String url = "jdbc:mysql://localhost:3306/messagebook?serverTimezone=GMT";
//		Connection connecter = null;
		try {
			// ���Udriver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// ���o�Ӹ�Ʈw�v���ós��
			connecter = (Connection) DriverManager.getConnection(url, "root", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return connecter;
	}

	// �����s�u
	public static void closeConnect() {
		if (connecter != null) {
			try {
				connecter.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void closeResult() {
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void closePreparedStatement() {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
