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
		// 不使用GET方法，直接呼叫POST
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

		// client傳入點選的留言板id
		String messageBookNo = null;
		try {
			// 取單一用法
			// 取request的留言板id json
			JSONObject jsonObj = new JSONObject(line);

			messageBookNo = Integer.toString(jsonObj.getInt("messageBookId"));
			System.out.println("json request mes id" + messageBookNo); // 取得留言板id

			// 取多值
//			JSONArray jsonarr = new JSONArray("[{\"message\":\"hahaihoa\",\"timeStamp\":\"2020-02-12 22:06:05\"},{\"message\":\"no2\",\"timeStamp\":\"2020-02-13 09:21:34\"}]");
//			for (int i = 0; i < jsonarr.length(); i++) {
//				JSONObject jsonOb = jsonarr.getJSONObject(i);
//				System.out.println("json many req: " + jsonOb.getString("message"));
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//如果沒有抓到messagebookid就沒有必要開連線浪費資源
		if (messageBookNo != null) {
			Connection connect = DBConnection.getConnect();
			//Statement statement = null;
			//ResultSet result = null;
			PreparedStatement preparedStatement = DBConnection.getPreparedStatement();
			ResultSet result = DBConnection.getResult();
			
			// 查詢該留言板的所有訊息與時間
			try {
				// 搜尋並以時間排序(由舊到新)
				String sqlQuery = "select messageBody, timeStamp, messageId " 
			+ "from message where messageBookId=?" + " order by timeStamp ASC;";
				
				preparedStatement =(PreparedStatement) connect.prepareStatement(sqlQuery);
				preparedStatement.setString(1, messageBookNo);
				
				result = preparedStatement.executeQuery();
				String res_json = null;
				List<MessageDataObject> list = QueryMessageDAO.queryMessageToList(result);

				// Gson lib 可將object轉換為json string(直接取obj參數為key,參數設定值為value)
				Gson gson = new Gson();
				res_json = gson.toJson(list);

				System.out.println(res_json);
				// 將json傳入res
				// 設定送出的編碼，避免中文亂碼
				response.setCharacterEncoding("UTF-8");
				response.getWriter().append(res_json).flush();

			} catch (SQLException e) {
				e.printStackTrace();
				DBConnection.closeResult();
				DBConnection.closePreparedStatement();
				DBConnection.closeConnect();
			} finally {
				//最後將連線的東西關掉
				DBConnection.closeResult();
				DBConnection.closePreparedStatement();
				DBConnection.closeConnect();
			}

		}
	}

}
