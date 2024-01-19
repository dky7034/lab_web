package com.itwill.springboot4.web;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.springboot4.domain.Comment;
import com.itwill.springboot4.dto.CommentRegisterRequestDto;
import com.itwill.springboot4.dto.CommentUpdateRequestDto;
import com.itwill.springboot4.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentRestController {
	
	private final CommentService commentSvc;
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ResponseEntity<Comment> registerComment(@RequestBody CommentRegisterRequestDto dto) {
		log.info("registeComment(dto={})", dto);
		
		// 서비스 메서드를 호출해서 댓글을 등록하고, 등록된 댓글을 응답으로 보냄.
		Comment entity = commentSvc.register(dto);
		log.info("id={}, createdTime={}", entity.getId(), entity.getCreatedTime());
		
		return ResponseEntity.ok(entity); // ResponseEntity에 entity를 보냄. (크롬 응답 확인)
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/all/{id}")
	public ResponseEntity<Page<Comment>> getCommentList(
			@PathVariable(name = "id") Long id,
			@RequestParam(name = "p", defaultValue = "0") int p) {
		log.info("===============================");
		log.info("getCommentList(id={}, p={})", id, p);
		log.info("===============================");
		
		// 서비스 메서드 호출 -> 포스트 아이디에 달려 있는 모든 댓글 목록을 페이징하여 가져옴.
		Page<Comment> list = commentSvc.getCommentList(id, p);
		
		return ResponseEntity.ok(list);
	}
	
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteComment(@PathVariable(name = "id") Long id) {
	// Long 타입의 ResponseEntity 객체를 반환하는 메서드.
	// 메서드 이름은 deleteComment.
	// 메서드의 파라미터로 URL 내부의 PathVariable을 전달함.
		log.info("deleteComment(id={})", id);
		
		// 서비스 메서드 호출 -> 포스트 아이디에 달려 있는 특정 댓글 삭제.
		commentSvc.deleteCommentById(id);
		return ResponseEntity.ok(id); // 삭제한 댓글의 아이디를 응답으로 보냄.
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/{id}")
	public ResponseEntity<Long> updateCommentById(
			@PathVariable(name = "id") Long id, 
			@RequestBody CommentUpdateRequestDto dto) {
	// Long 타입의 ResponseEntity 객체를 반환하는 메서드.
	// 메서드 이름은 updateCommentById.
	// 메서드의 파라미터로 URL 내부의 PathVariable 그리고 RequestBody를 전달함.
	// @pathVariable은 URL의 경로 변수에서 값을 추출하여 메서드의 파라미터로 전달.
	// @RequestBody는 HTTP 요청의 본문에서 객체를 추출하여 메서드의 파라미터로 전달.
		
		log.info("===============================================");
		log.info("updateComment(id={}, dto={})", id, dto);
		log.info("===============================================");
		
		// 흐름 이해하기...
		// PathVariable을 사용하여 URL에서 id 값을 받아옴.
		// RequestBody를 사용하여 dto 객체를 받아옴.
		// dto에 id 필드가 있는데 왜 굳이 id를 추출하여 전달했는가?...
		// id는 넘겨도 되고 안 넘겨도 상관 없음.
		// 단, 업데이트된 내용은 반드시 전달돼야 하므로 dto는 꼭 넘겨야 함!
		//-> 왜 굳이 dto를 사용했는가?
		// 서비스 클래스로 전달된 id는 사용해도, 사용하지 않아도 됨.
		// PathVariable의 id 값을 사용하지 않으면 dto의 아이디 값을 사용하면 됨.
		// DTO 객체의 생성 시점은 Spring이 HTTP 요청을 처리하고 해당 데이터를 DTO 객체로 변환할 때.
		//-> comments.js 소스코드를 보면 업데이트 버튼을 누르는 시점에 id와 text가 전달됨.
		//-> 즉, id와 text는 comments.js 소스코드에서 전달되며(정확히는 아니지만 대충ㅎㅎ) 이 때 dto가 만들어짐.
		
		// 서비스 메서드 호출 -> 포스트 아이디에 달려 있는 특정 댓글 업데이트.
		commentSvc.updateCommentById(id, dto);
		
		return ResponseEntity.ok(id); // 업데이트한 댓글의 아이디를 응답으로 보냄.
		
	}
	
}
