package com.itwill.jsp1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class CookieExController
 */
@WebServlet(name = "cookieExController", urlPatterns = { "/excookie" })
public class CookieExController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("cookieExController::doGet() 호출");
		
		// 쿠키 객체 생성 (쿠키에는 세션 아이디만 저장됨!)
		Cookie cookie1 = new Cookie("my-cookie", "안녕하세요"); // Cookie(이름, 값).
		
		// 생성된 쿠키 객체를 응답(response)에 포함.
		response.addCookie(cookie1);
		
		int count = 0; // 방문 횟수로 사용할 변수.
		
		// 클라이언트(브라우저)에서 보낸 쿠키(들)을 확인하는 방법:
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) { // 배열을 for문으로 순회하며 읽음.
			System.out.println("쿠키 이름: " + c.getName() + "=" + c.getValue());
			if (c.getName().equals("cnt")) { // 요청에서 쿠키에 cnt 라는 이름의 쿠키가 있으면...
				// "cnt" 이름의 쿠키가 있으면, 쿠키에 저장된 값(valuse)으로 count를 변경.
				count = Integer.parseInt(c.getValue());
			}
		}
				
		// 방문 횟수를 저장한 쿠키를 만들어서 응답에 포함.
		count++; // 방문 횟수 증가.
		
		request.setAttribute("count", count); // 방문 횟수를 요청 속성에 추가. -> JSP 에서 EL로 작성 가능해짐.
		
		Cookie cookie2 = new Cookie("cnt", String.valueOf(count)); // count + "" 도 데이터 타입을 문자열로 변경하게 함.
		response.addCookie(cookie2);
		
		request.getRequestDispatcher("/WEB-INF/cookie.jsp") // 저장할 폴더 경로
			.forward(request, response);
	}

}
