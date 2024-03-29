package com.itwill.jsp2.controller.post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.domain.Post;
import com.itwill.jsp2.service.PostService;

/**
 * Servlet implementation class PostDetailsController
 */
/* @WebServlet 어노테이션
 * 클라이언트가 url을 입력했을 때, 해당 url에 매칭하는 자바 파일이 실행되어야 한다.
 * 이러기 위해서는 url 매핑 기법을 사용해야 하며 그 기법에는 web.xml방식, 어노테이션 방식이 있다.
 * name: 서블릿의 이름을 설정하는 속성.
 * urlPatterns: 서블릿의 URL 목록을 설정하는 속성.
*/
@WebServlet(name = "postDetailsController", urlPatterns = { "/post/details" })
public class PostDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostDetailsController.class);
	
	private final PostService postService = PostService.getInstance();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet()");
		
		// 요청 파라미터 id를 찾음 -> DB에서 ID로 검색 -> 검색 결과를 뷰(view)에 전달.
		String param = request.getParameter("id"); // 요청 파라미터 id의 값을 읽음.
		Long id = Long.valueOf(param); // 요청 파라미터 값(문자열)을 Long 타입으로 변환.
		log.debug("--- id = {}", id);
		
		// 서비스(비즈니스) 계층의 메서드를 호출해서 뷰에 작성할 내용을 찾음.
		Post post = postService.read(id);
		
		// 요청 객체에 속성을 추가(저장).
		request.setAttribute("post", post);
		
		// 뷰로 전달.
		request.getRequestDispatcher("/WEB-INF/post/details.jsp")
			.forward(request, response);
		
	}
	
}