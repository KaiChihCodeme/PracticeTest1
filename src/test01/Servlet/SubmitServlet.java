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
			// Ū��POST�L�Ӫ��T��
			BufferedReader reader = request.getReader();
			line = reader.readLine();
			System.out.println("line: " + line.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// �Njson���������X��
		try {
			// ����@�Ϊk
			// ��request���d���Oid json
			JSONObject jsonObj = new JSONObject(line);
			getMessageBookId = jsonObj.getInt("messageBookId"); // ���o�d���Oid
			getMessage = jsonObj.getString("messageBody");// ���o�ǰe�d�����e
			// getTime = jsonObj.getString("timeStamp");//���o�ǰe�ɶ� (�o�̤���U���n�令TimeStamp)
			System.out.println("json request mes: " + getMessage);
			System.out.println("json request id: " + getMessageBookId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//�W�Ǹ�Ʀܸ�Ʈw
		SubmitMessageDAO.submitMessage(getMessage, getMessageBookId, getMessageId);
		// �^���w�W�Ǧ��\
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("Upload Completed").flush();

		//�W�ǧ������X�q���H
		NoticeMessageGMail mgmail = new NoticeMessageGMail();
		try {
			mgmail.sendMail(getMessage);
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
