package com.itwill.jsp1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class SecondServlet
 */

// 서블릿 URl(요청 주소) 매핑 방법:
// 1. web.xml (배포 관리자, deployment descriptor)에서 <servlet>, <servlet-mapping> 설정하거나,
// 2. 서블릿 클래스에서 @WebServlet 애너테이션으로 설정.
// (주의) web.xml 또는 애너테이션 둘 중 하나만 설정. 둘 다 설정할 수는 없음.
@WebServlet(name = "secondServlet", urlPatterns = { "/ex2" })
public class SecondServlet extends HttpServlet { // SecondServlet 클래스는 HttpServlet 클래스를 상속받음.
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	// HttpServletRequest 객체는 클라이언트의 요청 정보를 담고 있고, 
	// HttpServletResponse 객체는 서버에서 클라이언트로 응답을 보낼 때 사용.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("secondServlet::doGet() 메서드 호출");
		
		// 서버가 클라이언트로 보내는 응답(response)의 컨텐트 타입을 설정:
		// 한글 인코딩을 설정해서 한글이 깨지지 않도록 하기 위해서.
		response.setContentType("text/html; charset=utf-8");
		
		// PrintWriter out = response.getwriter(): 
		// 응답을 작성하기 위한 PrintWriter 객체를 얻음.
		// Servlet에서 HTTP 응답을 작성할 때 사용되는 코드의 일부로 Servlet에서 HTTP 응답을 작성하는 데 사용.
		// Servlet에서 클라이언트의 요청(Request)에 대한 응답(Response) 형태는 문자(character) 또는 바이트(byte)가 될 수 있다.
		// 클라이언트의 요청에 문자 형태로 응답하려면 데이터의 흐름(Stream)을 컨트롤해야 한다. 즉 텍스트(==문자) 형태로 응답을 보내도록 설정해야 한다.
		// HttpServletResponse 인터페이스의 상위 인터페이스인 ServletResponse의 getWriter() 메서드를 호출하면 스트림에 텍스트를 기록하는 것이 가능하다.
		PrintWriter out = response.getWriter();
		out.append("<hteml>")
			.append("<body>")
			.append("<h1>두번째 서블릿</h1>")
			.append("<a href='/jsp1'>인덱스 페이지</a>")
			.append("</body>")
			.append("</html>");
	}

}