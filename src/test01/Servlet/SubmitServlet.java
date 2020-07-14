package test01.Servlet;

import java.io.BufferedReader;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import test01.DAO.SubmitMessageDAO;

/**
 * Servlet implementation class SubmitServlet
 */
@WebServlet("/SubmitServlet")
public class SubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmitServlet() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String getMessage = null;
//		String getTime = null;
		int getMessageBookId = 0;
		int getMessageId = -1;

		String line = null;
		try {
			// 讀取POST過來的訊息
			BufferedReader reader = request.getReader();
			line = reader.readLine();
			System.out.println("line: " + line.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 將json內的欄位取出來
		try {
			// 取單一用法
			// 取request的留言板id json
			JSONObject jsonObj = new JSONObject(line);
			getMessageBookId = jsonObj.getInt("messageBookId"); // 取得留言板id
			getMessage = jsonObj.getString("messageBody");// 取得傳送留言內容
			// getTime = jsonObj.getString("timeStamp");//取得傳送時間 (這裡之後下面要改成TimeStamp)
			System.out.println("json request mes: " + getMessage);
			System.out.println("json request id: " + getMessageBookId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//上傳資料至資料庫
		SubmitMessageDAO.submitMessage(getMessage, getMessageBookId, getMessageId);
		// 回應已上傳成功
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("Upload Completed").flush();

		//上傳完成後丟出通知信
		NoticeMessageGMail mgmail = new NoticeMessageGMail();
		try {
			mgmail.sendMail(getMessage);
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
