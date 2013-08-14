package fiu.edu.pdp.tracker;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TrackerServlet
 */
@WebServlet("/TrackerServlet")
public class TrackerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrackerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String tracker = request.getParameter("tracker");
		String account = request.getParameter("account");
		if (account != null && account.length() > 0) {
			String pageUrl = request.getParameter("pageurl");
			String cookieId = request.getParameter("cookieid");
			String event = request.getParameter("event");
			String value = request.getParameter("value");
			HttpSession session = request.getSession();
			String sessionId = session.getId();
			String clientIP = request.getRemoteAddr();
			String clientHost =  request.getRemoteHost();
			Tracker.insertRecord(tracker, account, pageUrl, cookieId, event, value, sessionId, clientIP, clientHost);			
		}
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String tracker = request.getParameter("tracker");
		String account = request.getParameter("account");
		if (account != null && account.length() > 0) {
			String pageUrl = request.getParameter("pageurl");
			String cookieId = request.getParameter("cookieid");
			String event = request.getParameter("event");
			String value = request.getParameter("value");
			HttpSession session = request.getSession();
			String sessionId = session.getId();
			String clientIP = request.getRemoteAddr();
			String clientHost =  request.getRemoteHost();
			Tracker.insertRecord(tracker, account, pageUrl, cookieId, event, value, sessionId, clientIP, clientHost);			
		}
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
	}

}
