package com.itwill.jsp2.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.dto.UserSignUpDto;
import com.itwill.jsp2.service.UserService;

/**
 * Servlet implementation class UserSignUpController
 */
@WebServlet(name = "userSignUpController", urlPatterns = { "/user/signup" })
public class UserSignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(UserSignUpController.class);
	
	private final UserService userService = UserService.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("deGet()");
		
		request.getRequestDispatcher("/WEB-INF/user/signup.jsp")
			.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost()");
		
		// 요청 파라미터 userid, password, email를 찾음.
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		// builder pattern 사용!
		UserSignUpDto dto = UserSignUpDto.builder().userid(userid).password(password).email(email).build();
		log.debug("dto={}", dto);
		
		// 서비스 메서드를 호출하면서 회원 가입 정보를 전달.
		boolean result = userService.signUp(dto);
		
		// 뷰로 이동. (성공 or 실패 여부에 따라서)
		if (result) { // 회원 가입 성공.
			response.sendRedirect(request.getContextPath()); // context root로 이동(리다이렉트). 메인 페이지로 이동함.
		} else { // 회원 가입 실패
			String url = request.getContextPath() + "/user/signup?result=fail";
			response.sendRedirect(url); // 회원 가입 페이지로 이동.
		}
		
	}

}
