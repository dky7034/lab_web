package com.itwill.jsp2.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class HomeController
 */
/* @WebServlet 어노테이션
 * 클라이언트가 url을 입력했을 때, 해당 url에 매칭하는 자바 파일이 실행되어야 한다.
 * 이러기 위해서는 url 매핑 기법을 사용해야 하며 그 기법에는 web.xml방식, 어노테이션 방식이 있다.
 * name: 서블릿의 이름을 설정하는 속성.
 * urlPatterns: 서블릿의 URL 목록을 설정하는 속성.
*/
@WebServlet(name = "homeController", urlPatterns = { "" })
// "http://localhost:8081/jsp2" context root(=context path)를 처리하는 서블릿
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("homeController::doGet()");
		log.debug("doGet()");
		
		// 아래 코드는 Java Servlet에서 사용되는 코드입니다. 코드는 웹 어플리케이션에서 특정 JSP(JavaServer Pages) 페이지로 사용자를 전송하는 데 사용됩니다.
		// request.getRequestDispatcher("/WEB-INF/home.jsp")는 현재 요청과 관련된 RequestDispatcher를 얻습니다. 
		// RequestDispatcher: 클라이언트로부터 요청을 받고 이를 다른 리소스(서블릿, html, jsp)로 넘겨주는 역할을 하는 인터페이스.
		// forward(): RequestDispatcher 인터페이스의 역할을 수행하는 메서드. 
		// -> 클라이언트 요청에 의해 컨테이너에서 생성된 request와 response를 다른 리소스(서블릿, html, jsp)로 넘겨줌.
		// 여기서 "/WEB-INF/home.jsp"는 전송하려는 JSP 페이지의 경로입니다.
		// 일반적으로 "/WEB-INF" 디렉토리는 직접 웹에서 접근할 수 없는 위치에 있어서 보안상의 이유로 JSP 페이지를 숨기기 위해 사용됩니다.
		// 그 후 .forward(request, response)는 현재의 요청(request) 및 응답(response) 객체를 전달하여 지정된 JSP 페이지로 제어를 이전시킵니다.
		// JSP 페이지는 사용자에게 보여지는 콘텐츠를 생성하고 응답으로 제공됩니다.
		// 이 코드는 일반적으로 웹 어플리케이션에서 로그인 후 사용자를 홈페이지 또는 대시보드로 리디렉션하는 데 사용될 수 있습니다.
		// 요청(request)을 뷰(view)로 전달.
		request.getRequestDispatcher("/WEB-INF/home.jsp")
			.forward(request, response);
	}
	
}
