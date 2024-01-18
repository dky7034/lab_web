package com.itwill.springboot4.web;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping
	public ResponseEntity<Comment> registerComment(@RequestBody CommentRegisterRequestDto dto) {
		log.info("registeComment(dto={})", dto);
		
		// 서비스 메서드를 호출해서 댓글을 등록하고, 등록된 댓글을 응답으로 보냄.
		Comment entity = commentSvc.register(dto);
		log.info("id={}, created={}", entity.getId(), entity.getCreatedTime());
		
		return ResponseEntity.ok(entity); // ResponseEntity에 entity를 보냄. (크롬 응답 확인)
	}
	
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteComment(@PathVariable(name = "id") Long id) {
		log.info("deleteComment(id={})", id);
		
		// 서비스 메서드 호출 -> 포스트 아이디에 달려 있는 특정 댓글 삭제.
		commentSvc.deleteCommentById(id);
		
		return ResponseEntity.ok(id); // 삭제한 댓글의 아이디를 응답으로 보냄.
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Long> updateCommentById(@PathVariable(name = "id") Long id, 
												@RequestBody CommentUpdateRequestDto dto) {
		log.info("updateComment(dto={})", dto);
		
		// 서비스 메서드 호출 -> 포스트 아이디에 달려 있는 특정 댓글 업데이트.
		commentSvc.updateCommentById(dto);
		
		return ResponseEntity.ok(id); // 업데이트한 댓글의 아이디를 응답으로 보냄.
		
	}
	
}
