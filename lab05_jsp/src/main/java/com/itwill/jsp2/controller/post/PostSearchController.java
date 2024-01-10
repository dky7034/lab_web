package com.itwill.jsp2.controller.post;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.dto.PostListItemDto;
import com.itwill.jsp2.service.PostService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PostSearchController
 */
/* @WebServlet 어노테이션
 * 클라이언트가 url을 입력했을 때, 해당 url에 매칭하는 자바 파일이 실행되어야 한다.
 * 이러기 위해서는 url 매핑 기법을 사용해야 하며 그 기법에는 web.xml방식, 어노테이션 방식이 있다.
 * name: 서블릿의 이름을 설정하는 속성.
 * urlPatterns: 서블릿의 URL 목록을 설정하는 속성.
*/
@WebServlet(name = "postSearchController", urlPatterns = { "/post/search" })
public class PostSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PostSearchController.class);

	private final PostService postService = PostService.getInstance();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
		log.debug("doGet()");
		
		// 요청 파라미터 catetory(검색 타입), keyword(검색어) 찾기:
		String category = request.getParameter("category");
		String keyword = request.getParameter("keyword");
		log.debug("--- catetory={}, keyword={}", category, keyword);
		
		// 서비스 호출
		List<PostListItemDto> list = postService.search(category, keyword);
		// 검색 결과를 요청 속성(request attribute)에 추가.
		request.setAttribute("posts", list);
		
		// 검색 결과 페이지(뷰)로 전달.
		request.getRequestDispatcher("/WEB-INF/post/list.jsp")
		    .forward(request, response);
	}

}