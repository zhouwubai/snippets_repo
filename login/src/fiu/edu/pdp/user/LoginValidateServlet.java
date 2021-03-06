package fiu.edu.pdp.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class LoginValidateServlet
 */
@WebServlet("/LoginValidateServlet")
public class LoginValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginValidateServlet() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		
		System.out.println(username);
		System.out.println(password);
		JsonObject result = new JsonObject();
		User user = User.getFromSession(request.getSession());
		
		if(user != null){
			result.addProperty("result", "true");
			result.addProperty("nick_name", user.getNick_name());
		}else{
			if(User.userValidate(username, password,request.getSession())){
				result.addProperty("result", "true");
				user = User.getFromSession(request.getSession());
				result.addProperty("nick_name", user.getNick_name());
			}else{
				result.addProperty("result", "false");
			}
		}
		
		response.getWriter().write(result.toString());
	}

}
