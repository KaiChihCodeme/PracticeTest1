package test01.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import test01.Connection.DBConnection;

public class DeleteMessageDAO {

	public static void deleteMessage(int getMessageId) {
		try {
			Connection connect = DBConnection.getConnect();
			PreparedStatement ps = DBConnection.getPreparedStatement();
			// ¥HPK§R°£
			String sqlDelete = "delete from message where messageId=?;";
			ps = (PreparedStatement) connect.prepareStatement(sqlDelete);
			ps.setInt(1, getMessageId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			DBConnection.closePreparedStatement();
			DBConnection.closeConnect();
		} finally {
			DBConnection.closePreparedStatement();
			DBConnection.closeConnect();
		}
	}
}
