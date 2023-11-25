package com.itwill.jsp1.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.itwill.jsp1.model.Contact;

/**
 * Servlet implementation class ContactServlet
 */
// 서블릿 매핑:
// 실제 서블릿 클래스 파일 이름을 대체할 수 있는 별명을 붙여주는 것!
// 브라우저는 URL을 이용해 웹 프로젝트를 서버에게 요청한다. 이때 URL은 다음과 같은 구성으로 이루어져 있다.
// http:// IP주소:포트번호 / 프로젝트이름 / < 패키지이름 >.< 클래스이름 >
// 그런데 클래스 이름이 길어지면 입력하기도 불편하고, 
// 클래스 이름이 노출되며 어떤 기능을 하는지 쉽게 파악할 수 있기 때문에 보안에도 취약하다. 
// 그래서 이러한 문제점을 방지하기 위해 실제 서블릿 클래스 파일 이름을 대체할 수 있는 별명을 붙여주는 것이다.
@WebServlet(name = "contactServlet", urlPatterns = { "/mvc" })
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("contactServlet::doGet() 호출");
		
		// 컨트롤러에서 뷰(view)를 호출(뷰로 요청을 전달).
		request.getRequestDispatcher("/WEB-INF/contact_form.jsp")
			.forward(request, response); 
			// view 로 전달. WAS가 전달해준 request, response 를 그대로 전달(request, response 유지)
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("contactServlet::doPost() 호출");
		
		// 클라이언트가 보낸 요청 파라미터들을 읽음.
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		
		// Contact 타입 객체 생성. (위에서 요청 파라미터들을 읽었으므로 생성 가능해짐)
		Contact contact = new Contact(id, name, phone, email);
		System.out.println(contact);
		
		// 연락처 DB에 저장. (했다 치고...)
		
		// 인덱스 페이지로 리다이렉트(redirect). (forward 는 주소가 변하지 않으므로 리다이렉트를 사용하는게 더 좋지 않을까~)
		response.sendRedirect("/jsp1");
	}

}
