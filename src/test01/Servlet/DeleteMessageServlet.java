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
			// Ū��POST�L�Ӫ��T��
			BufferedReader reader = request.getReader();
			line = reader.readLine();
			System.out.println("DeleteServlet line: " + line.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// ����@�Ϊk
			// ��request���d��id json
			JSONObject jsonObj = new JSONObject(line);
			getMessageId = jsonObj.getInt("messageId");
			System.out.println("json delete request message id" + getMessageId); // ���o�d���Oid
		} catch (Exception e) {
			e.printStackTrace();
		}

		//�ܸ�Ʈw�R�����
		DeleteMessageDAO.deleteMessage(getMessageId);
		// �^���w�R�����\
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("delete completed").flush();

	}

}
