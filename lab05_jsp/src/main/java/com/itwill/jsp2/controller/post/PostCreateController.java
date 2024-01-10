package com.itwill.jsp2.controller.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.dto.PostCreateDto;
import com.itwill.jsp2.service.PostService;

/**
 * Servlet implementation class PostCreateController
 */
/* @WebServlet 어노테이션
 * 클라이언트가 url을 입력했을 때, 해당 url에 매칭하는 자바 파일이 실행되어야 한다.
 * 이러기 위해서는 url 매핑 기법을 사용해야 하며 그 기법에는 web.xml방식, 어노테이션 방식이 있다.
 * name: 서블릿의 이름을 설정하는 속성.
 * urlPatterns: 서블릿의 URL 목록을 설정하는 속성.
*/
@WebServlet(name = "postCreateController", urlPatterns = { "/post/create" })
public class PostCreateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostCreateController.class);
	private final PostService postService = PostService.getInstance();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet()");
		
		request.getRequestDispatcher("/WEB-INF/post/create.jsp")
			.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost()");
		
		// 요청 파라미터에서 title, content, author 를 찾음.
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String author = request.getParameter("author");
		log.debug("--- req params: title={}, content={}, author={}",
				title, content, author);
		
		// PostCreateDto 타입 객체 생성.
		PostCreateDto dto = new PostCreateDto(title, content, author);
		
		// 서비스 계층의 메서드를 호출해서 새 포스트를 저장. (db에 저장됨)
		postService.create(dto);
		
		// 포스트 목록 페이지로 리다이렉트.
		String url = request.getContextPath() + "/post/list" ; // getContextPath(): context root를 가져옴.
		log.debug("redirected url = {}", url);
		response.sendRedirect(url);
	}
	
}
