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
			// ���d�̤j��id�O�h��
			String sqlQueryMaxId = "select max(messageId) from message;";
			statement = (Statement) connect.createStatement();

			result_maxId = statement.executeQuery(sqlQueryMaxId);

			if (result_maxId.next()) {
				// �d�ߨ�̤j��id+1�����s�W���T��id
				getMessageId = (result_maxId.getInt("max(messageId)")) + 1;
			}

			String sqlInsert = "insert into message (messageBody, messageBookId, timeStamp, messageId) "
					+ "values (?,?,?,?);";

			// �N�Ѽƶ�J�ܻy�k��
			pst = connect.prepareStatement(sqlInsert);
			pst.setString(1, getMessage);
			pst.setInt(2, getMessageBookId);
			// �b�o��s�@�ثe�ɶ�
			Timestamp now = new Timestamp(System.currentTimeMillis());
			pst.setTimestamp(3, now);
			// �s�@message id
			pst.setInt(4, getMessageId);
			// �����W�Ǹ��log
			System.out.println("update data: " + getMessage + "/" + getMessageBookId + "/" + now + "/" + getMessageId);
			// �W�Ǧܸ�Ʈw
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
