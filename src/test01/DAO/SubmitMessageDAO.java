package test01.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import test01.Connection.DBConnection;

public class SubmitMessageDAO {
	public static void submitMessage(String getMessage
			, int getMessageBookId, int getMessageId) {
		Connection connect = DBConnection.getConnect();
		ResultSet result_maxId = null;
		PreparedStatement pst = null;
		Statement statement= null;
		try {
			// 先查最大的id是多少
			String sqlQueryMaxId = "select max(messageId) from message;";
			statement = (Statement) connect.createStatement();

			result_maxId = statement.executeQuery(sqlQueryMaxId);

			if (result_maxId.next()) {
				// 查詢到最大的id+1為此新增之訊息id
				getMessageId = (result_maxId.getInt("max(messageId)")) + 1;
			}

			String sqlInsert = "insert into message (messageBody, messageBookId, timeStamp, messageId) "
					+ "values (?,?,?,?);";

			// 將參數填入至語法中
			pst = connect.prepareStatement(sqlInsert);
			pst.setString(1, getMessage);
			pst.setInt(2, getMessageBookId);
			// 在這邊製作目前時間
			Timestamp now = new Timestamp(System.currentTimeMillis());
			pst.setTimestamp(3, now);
			// 製作message id
			pst.setInt(4, getMessageId);
			// 本次上傳資料log
			System.out.println("update data: " + getMessage + "/" + getMessageBookId + "/" + now + "/" + getMessageId);
			// 上傳至資料庫
			pst.executeUpdate();
			pst.clearParameters();
		} catch (SQLException e) {
			e.printStackTrace();
			if (result_maxId != null) {
				try {
					result_maxId.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			}
			
			DBConnection.closeConnect();
		} finally {
			if (result_maxId != null) {
				try {
					result_maxId.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			DBConnection.closeConnect();

		}
	}

}
