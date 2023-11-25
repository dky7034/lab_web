package com.itwill.jsp2.controller.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.dto.PostUpdateDto;
import com.itwill.jsp2.service.PostService;

/**
 * Servlet implementation class PostUpdateController
 */
/* @WebServlet 어노테이션
 * 클라이언트가 url을 입력했을 때, 해당 url에 매칭하는 자바 파일이 실행되어야 한다.
 * 이러기 위해서는 url 매핑 기법을 사용해야 하며 그 기법에는 web.xml방식, 어노테이션 방식이 있다.
 * name: 서블릿의 이름을 설정하는 속성.
 * urlPatterns: 서블릿의 URL 목록을 설정하는 속성.
*/
@WebServlet(name = "postUpdateController", urlPatterns = { "/post/update" })
public class PostUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostUpdateController.class);
	
	private final PostService postService = PostService.getInstance();
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost()");
		
		// 요청 파라미터 id, title, content를 찾음 (jsp의 name 속성 값)
		// 크롬 개발자 도구(f12) - 네트워크 - 페이로드에서 확인 가능.
		Long id = Long.valueOf(request.getParameter("id"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		PostUpdateDto dto = new PostUpdateDto(id, title, content);
		log.debug("dto={}", dto);
		
		// 서비스(비즈니스) 계층의 메서드를 호출 -> 포스트 업데이트.
		postService.update(dto);
		
		// 상세보기 페이지로 리다이렉트.
		String url = request.getContextPath() + "/post/details?id=" + id;  // request.getContextPath()는 /jsp2 까지 줌.
		response.sendRedirect(url);
	}

}
