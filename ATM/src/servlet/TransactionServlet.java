package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import atm.ATM;
import atm.Session;

/**
* 选择一个交易（存款，取款，查询）
* @author  何希
* @version 10/06/2018
*/
public class TransactionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Session currentSession = ATM.getInstance().getSession();
		int options = Integer.parseInt(req.getParameter("num"));
		currentSession.selectTransaction(options);
		String json = ATM.getInstance().getResponse();
//		System.out.println(json);
		resp.setContentType("text/json");  
		resp.setCharacterEncoding("UTF-8"); 
		resp.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
