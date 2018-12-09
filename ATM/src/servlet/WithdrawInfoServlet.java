package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import atm.ATM;
import atm.Session;
import banking.Account;

/**
* 获取取款金额,执行扣款
* @author  何希
* @version 10/06/2018
*/
public class WithdrawInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int num = Integer.parseInt(req.getParameter("num"));
		int transaction = Integer.parseInt(req.getParameter("transaction"));
		switch(transaction){
		case 1:
	//		System.out.println(ATM.getInstance().getSession().getTransaction().getAccount().getBalance());
			ATM.getInstance().getSession().getTransaction().setAmount(num);
			try {
				ATM.getInstance().getSession().getTransaction().execute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			ATM.getInstance().getSession().getTransaction().setAmount(num);
			ATM.getInstance().getSession().getTransaction().deposit();
			break;
		case 3:
			ATM.getInstance().getSession().getTransaction().TransferTransaction(num);
			break;
		case 4:
			ATM.getInstance().getSession().getTransaction().setAmount(num);
			ATM.getInstance().getSession().getTransaction().transfer();
			break;
		}
		
	//	System.out.println(transaction);
		String json = ATM.getInstance().getResponse();
		resp.setContentType("text/json");  
		resp.setCharacterEncoding("UTF-8"); 
		resp.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
