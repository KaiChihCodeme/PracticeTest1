package test01.Servlet;

import java.io.IOException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import test01.Connection.DBConnection;
import test01.DAO.QueryMessageDAO;
import test01.DataObject.MessageDataObject;

import org.json.JSONObject;
import java.io.BufferedReader;

/**
 * Servlet implementation class DataServlet
 */
@WebServlet("/DataServlet")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		// ���ϥ�GET��k�A�����I�sPOST
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

//		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			line = reader.readLine();

			System.out.println("line: " + line.toString());

//			while (line != null) {
//				sb.append(line);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// client�ǤJ�I�諸�d���Oid
		String messageBookNo = null;
		try {
			// ����@�Ϊk
			// ��request���d���Oid json
			JSONObject jsonObj = new JSONObject(line);

			messageBookNo = Integer.toString(jsonObj.getInt("messageBookId"));
			System.out.println("json request mes id" + messageBookNo); // ���o�d���Oid

			// ���h��
//			JSONArray jsonarr = new JSONArray("[{\"message\":\"hahaihoa\",\"timeStamp\":\"2020-02-12 22:06:05\"},{\"message\":\"no2\",\"timeStamp\":\"2020-02-13 09:21:34\"}]");
//			for (int i = 0; i < jsonarr.length(); i++) {
//				JSONObject jsonOb = jsonarr.getJSONObject(i);
//				System.out.println("json many req: " + jsonOb.getString("message"));
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//�p�G�S�����messagebookid�N�S�����n�}�s�u���O�귽
		if (messageBookNo != null) {
			Connection connect = DBConnection.getConnect();
			//Statement statement = null;
			//ResultSet result = null;
			PreparedStatement preparedStatement = DBConnection.getPreparedStatement();
			ResultSet result = DBConnection.getResult();
			
			// �d�߸ӯd���O���Ҧ��T���P�ɶ�
			try {
				// �j�M�åH�ɶ��Ƨ�(���¨�s)
				String sqlQuery = "select messageBody, timeStamp, messageId " 
			+ "from message where messageBookId=?" + " order by timeStamp ASC;";
				
				preparedStatement =(PreparedStatement) connect.prepareStatement(sqlQuery);
				preparedStatement.setString(1, messageBookNo);
				
				result = preparedStatement.executeQuery();
				String res_json = null;
				List<MessageDataObject> list = QueryMessageDAO.queryMessageToList(result);

				// Gson lib �i�Nobject�ഫ��json string(������obj�ѼƬ�key,�ѼƳ]�w�Ȭ�value)
				Gson gson = new Gson();
				res_json = gson.toJson(list);

				System.out.println(res_json);
				// �Njson�ǤJres
				// �]�w�e�X���s�X�A�קK����ýX
				response.setCharacterEncoding("UTF-8");
				response.getWriter().append(res_json).flush();

			} catch (SQLException e) {
				e.printStackTrace();
				DBConnection.closeResult();
				DBConnection.closePreparedStatement();
				DBConnection.closeConnect();
			} finally {
				//�̫�N�s�u���F������
				DBConnection.closeResult();
				DBConnection.closePreparedStatement();
				DBConnection.closeConnect();
			}

		}
	}

}
