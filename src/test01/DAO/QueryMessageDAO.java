package test01.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import test01.DataObject.MessageDataObject;

public class QueryMessageDAO {
	public static List<MessageDataObject> queryMessageToList(ResultSet result) {
		
		List<MessageDataObject> list = new ArrayList<MessageDataObject>();

		// �ݦC�X�C�ӷj�M���G
		try {
			while (result.next()) {
				// SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				// String dateString = dateFormat.format(result.getTimestamp("timeStamp"));
				System.out.println(result.getString("messageBody") + " / " + result.getString("timeStamp") + " / "
						+ result.getInt("messageId"));

				// �N�C�����G�[�Jjson
				MessageDataObject messageOb = new MessageDataObject();
				messageOb.setMessage(result.getString("messageBody"));
				messageOb.setTimeStamp(result.getString("timeStamp"));
				messageOb.setMessageId(result.getInt("messageId"));
				list.add(messageOb);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
