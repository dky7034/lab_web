package com.itwill.jsp1;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// "example.jsp"로의 포워딩을 통해 HTML 코드를 생성하는 것을 서블릿에서 처리하는 코드.

/**
 * Servlet implementation class ForwardServlet
 */
// ForwardServlet이라는 서블릿 클래스 정의
// "/ex3" URL 패턴에 매핑.
@WebServlet(name = "forwardServlet", urlPatterns = { "/ex3" })
public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	// HTTP GET 요청에 대한 처리를 위해 doGet 메서드를 오버라이딩.
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HttpServletRequest 객체는 클라이언트의 요청 정보를 담고 있고, 
    	// HttpServletResponse 객체는 서버에서 클라이언트로 응답을 보낼 때 사용됨.
		System.out.println("forwardServlet::doGet() 호출");
		
		// 서블릿 클래스에서 HTML 코드를 작성하는 것은 너무 번거로움.
		// JSP에서 HTML 코드를 생성하는 것이 좋음.
		// 클라이언트에서 서버로 온 요청을 "forward" 방식으로 이동:
		// - 같은 WAS(웹 애플리케이션 서버)의 같은 웹 애플리케이션 안에서만 페이지를 이동하는 방식.
		// - 요청 주소(URL)는 바뀌지 않음.
		// - request, response 객체가 유지됨.
		// - 다른 WAS 또는 다른 웹 애플리케이션의 페이지로 포워드(forward)할 수 없음!
		
		// src/main/webapp 폴더 아래의 파일 경로와 파일 이름 아규먼트로 전달:
		request.getRequestDispatcher("example.jsp") // 다른 자원(주로 JSP)으로 받은 요청 정보(request)를 전달하는 것.
			.forward(request, response); // forward 메서드를 호출하여 현재 서블릿에서 받은 request와 response 객체를 다른 자원(여기서는 JSP 파일)으로 전달.
	}

}