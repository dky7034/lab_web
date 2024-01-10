package com.itwill.jsp2.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class UserSignOutController
 */
// userSignOutController 서블릿을 URL 패턴 /user/signout에 매핑.
// 
@WebServlet(name = "userSignOutController", urlPatterns = { "/user/signout" })
public class UserSignOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(UserSignOutController.class);
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet()");
		
		// 로그아웃: 
		// (1) 세션에 저장된 로그인 관련 정보 삭제
		// (2) 세션 객체 무효화
		HttpSession session = request.getSession();
		// (1) setAttribute(name, value)를 호출할 때 사용했던 속성 이름(name)으로 제거. -> UserSignInController.java 확인.
		session.removeAttribute("signedInUser");
		// (2) 세션 객체 무효화 (서버에서 저장하고 있던 세션을 무효화, 세션 소멸)
		session.invalidate();
		
		// 로그아웃 이후에는 홈 페이지로 이동.
		response.sendRedirect(request.getContextPath()); // 홈(메인 페이지)으로 이동(리다이렉트).
	}

}