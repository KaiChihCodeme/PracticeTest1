package test01.Servlet;

import java.io.BufferedReader;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import test01.DAO.DeleteMessageDAO;

/**
 * Servlet implementation class DeleteMessageServlet
 */
@WebServlet("/DeleteMessageServlet")
public class DeleteMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteMessageServlet() {
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
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int getMessageId = -1;
		String line = null;
		try {
			// 讀取POST過來的訊息
			BufferedReader reader = request.getReader();
			line = reader.readLine();
			System.out.println("DeleteServlet line: " + line.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 取單一用法
			// 取request的留言id json
			JSONObject jsonObj = new JSONObject(line);
			getMessageId = jsonObj.getInt("messageId");
			System.out.println("json delete request message id" + getMessageId); // 取得留言板id
		} catch (Exception e) {
			e.printStackTrace();
		}

		//至資料庫刪除資料
		DeleteMessageDAO.deleteMessage(getMessageId);
		// 回應已刪除成功
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("delete completed").flush();

	}

}
