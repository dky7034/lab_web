package com.itwill.spring2.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.spring2.domain.Post;
import com.itwill.spring2.dto.post.PostCreateDto;
import com.itwill.spring2.dto.post.PostListItemDto;
import com.itwill.spring2.dto.post.PostSearchDto;
import com.itwill.spring2.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor //-> final 필드들을 초기화하는 아규먼트를 갖는 생성자.
@Controller
@RequestMapping("/post")
//-> PostController의 컨트롤러 메서드의 매핑 URL(주소)은 "/post"로 시작.
public class PostController {
	// PostService 객체를 주입받음.
	// (1) 애너테이션을 사용한 의존성 주입.
//	@Autowired private PostService postService; // 객체 생성 권한이 Spring FrameWork로 이전됨. (의존성 주입, 제어의 역전)
	// Spring Container가 가지고 있는 빈(bean = 객체) 중에서 타입에 맞는 객체를 주입해줌.
	
	// (2) 생성자에 의한 의존성 주입. @RequiredArgsConstructor 사용.
	private final PostService postService; // 객체 생성 권한이 Spring FrameWork로 이전됨. (의존성 주입, 제어의 역전)
	// Spring Container가 가지고 있는 빈(bean = 객체) 중에서 타입에 맞는 객체를 주입해줌.
	
	@GetMapping("/list") //-> GET 방식의 "/post/list" 요청 주소를 처리하는 메서드.
	public void list(Model model) { 
		//-> 디스패쳐 서블릿에게 뷰에 전달할 데이터를 저장할 모델 객체를 요청해서 받음.
		// model: 데이터를 담는 그릇 역할, map 구조로 저장됨, key와 value로 구성.
		
		// TODO: postService의 메서드를 호출해서 포스트 목록을 만들고, 뷰에 전달.
		List<PostListItemDto> list = postService.read();
		model.addAttribute("posts", list); //-> 뷰에 전달되는 데이터.
		
		log.debug("list size = {}", list.size());
			
		// 리턴 값이 없으면 요청 경로로 뷰(JSP)를 찾음.
		//-> /WEB-INF/views/post/list.jsp
	}
	
	@GetMapping("/create") //-> GET 방식의 "/post/create" 요청 주소를 처리하는 메서드.
	public void create() {
		log.debug("GET - create()");
	}
	
	@PostMapping("/create") //-> POST 방식의 "/post/create" 요청 주소를 처리하는 메서드.
	public String create(PostCreateDto dto) { // 사용자로부터 입력받은 dto를 아규먼트로 전달.
		log.debug("POST - create(dto={})", dto);
		
		// 서비스 계층의 메서드를 호출해서 새 포스트 작성 서비스를 수행.
		postService.create(dto);
		
		return "redirect:/post/list"; // 포스트 목록 페이지로 이동(redirect).
	}
	
	@GetMapping({"/details", "/modify"})
	//-> /post/details, /post/modify 2개의 요청을 처리하는 메서드!
	public void details(@RequestParam(name = "id") long id, Model model) {
		log.debug("details(id={})", id);
		
		// 서비스 계층의 메서드를 호출해서 뷰에 전달할 포스트 상세보기 내용을 읽음.
		Post post = postService.details(id);
		
		// 뷰에 데이터를 전달하기 위해서 모델에 데이터를 속성으로 추가.
		model.addAttribute("post", post);
	}
	 
	@GetMapping("/delete") //-> GET 방식의 "/post/delete" 요청 주소를 처리하는 메서드.
	/*
	 * 누가.. delete 요청 주소를 보냈을까? -> 자바스크립트 파일에서 보냄.
	 * post-modify.js 파일에서 location.href = `delete?id=${inputId.value}`; 가 실행되어,
	 * localhost:8081/spring2/delete?id=(번호) 요청이 서버로 전달된 것이다!
	 */
	public String delete(long id) {
		
		postService.delete(id);
		
		return "redirect:/post/list";
	}
	
	@PostMapping("/update") //-> GET 방식의 "/post/update" 요청 주소를 처리하는 메서드.
	/*
	 * 누가.. update 요청 주소를 보냈을까? -> 자바스크립트 파일에서 보냄.
	 * post-modify.js 파일에서 form.action = 'update'; 가 실행되어,
	 * localhost:8081/spring2/post/update 요청이 서버로 전달된 것이다!
	 */
	public String update(Post post) {
		
		postService.update(post);
		
		return "redirect:/post/list";
	}
	
	@GetMapping("/search") //-> GET 방식의 "/post/search" 요청 주소를 처리하는 메서드.
	public String search(PostSearchDto dto, Model model) {
    	log.debug("search(dto={})", dto);
    
		// 서비스 계층의 메서드를 호출해서 검색 서비스를 수행.
    	List<PostListItemDto> list = postService.search(dto);
    
    	// 검색 결과를 뷰에 전달하기 위해서 모델 속성(attribute)에 추가.
    	model.addAttribute("posts", list);
    
    	return "post/list"; //-> 뷰의 경로(/WEB-INF/views/post/list.jsp)
    	//-> servlet-context에서 prefix 부분이 /WEB-INF/views/ 였으므로.
	}
	
}