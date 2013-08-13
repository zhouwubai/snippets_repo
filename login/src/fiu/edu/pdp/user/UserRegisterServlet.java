package fiu.edu.pdp.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class UserRegisterServlet
 */
@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegisterServlet() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("user_email");
		String nickName = request.getParameter("nick_name");
		String password = request.getParameter("user_password");

		JsonObject jsonObj = new JsonObject();
		if(UserRegister.checkEmailExist(email)){
			jsonObj.addProperty("result", "invalid"); //duplicate email
			jsonObj.addProperty("msg", "此邮箱地址已存在，请使用其它邮箱");
		}else{
			if(UserRegister.registerUser(email, nickName, password)){
				// register successfully
				jsonObj.addProperty("result", "success");
				jsonObj.addProperty("msg", "注册成功!");
			}else{
				jsonObj.addProperty("result", "fail");
				jsonObj.addProperty("msg", "系统出现故障，请重新提交!");
			}
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(jsonObj.toString());
	}
	
	
	

}
