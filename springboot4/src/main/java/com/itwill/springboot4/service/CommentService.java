package com.itwill.springboot4.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itwill.springboot4.domain.Comment;
import com.itwill.springboot4.domain.CommentRepository;
import com.itwill.springboot4.domain.Post;
import com.itwill.springboot4.domain.PostRepository;
import com.itwill.springboot4.dto.CommentRegisterRequestDto;
import com.itwill.springboot4.dto.CommentUpdateRequestDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
	
	private final CommentRepository commentDao;
	private final PostRepository postDao;

	public Comment register(CommentRegisterRequestDto dto) {
		log.info("register(dto={})", dto);
		
		// 댓글을 달 포스트 엔터티를 찾음.
		Post post = postDao.findById(dto.getPostId()).orElseThrow();
		
		// DB에 insert할 댓글 엔터티를 생성.
		Comment entity = Comment.builder()
				.post(post)
				.text(dto.getText())
				.writer(dto.getWriter())
				.build();
		
		// DB에 엔터티를 저장: insert 쿼리를 실행.
		commentDao.save(entity);
		
		// 저장된 엔터티를 리턴.
		return entity;
	}
	
	// 댓글들의 페이지를 리턴하는 메서드.
	public Page<Comment> getCommentList(Long postId, int page) {
		log.info("getCommentList(postId={}, page={})", postId, page);
		
		// 댓글이 달려 있는 포스트 엔터티를 찾음.
		Post post = postDao.findById(postId).orElseThrow();
		
		// 포스트의 댓글 목록을 검색. 페이징과 정렬이 적용된 댓글 목록.
		Pageable pageable = PageRequest.of(page, 5, Sort.by("modifiedTime").descending()); // 엔터티의 필드 이름 사용!
		Page<Comment> data = commentDao.findByPost(post, pageable);
		log.info("댓글 페이징 서비스 메서드 로그:",
				data.getContent(), //-> 현재 페이지의 댓글 리스트.
				data.getNumber(), //-> 현재 페이지 번호.
				data.getNumberOfElements(), //-> 현재 페이지에 있는 원소(댓글)의 개수.
				data.getTotalElements(), //-> 전체 원소(댓글)의 개수.
				data.getTotalPages()); //-> 전체 페이지의 개수.
		
		// ========================================== //
		// Page<T>.getNumber(): 현재 페이지 번호.
		// Page<T>.getTotalElements(): 전체 원소 개수.
		// Page<T>.getTotalPages(): 전체 페이지 개수.
		// ========================================== //
		
		return data;
	}

	public void deleteCommentById(Long id) {
		log.info("deleteCommentById(id={})", id);
		
		commentDao.deleteById(id);
	}
	
	@Transactional
	public void updateCommentById(CommentUpdateRequestDto dto) {
		log.info("updateCommentById(dto={})", dto);
		
		Comment entity = commentDao.findById(dto.getId()).orElseThrow();
		entity.update(dto.getText());
//		commentDao.save(entity); //-> 필요X: @Transactional에 의해 변경된 엔터티는 자동으로 저장됨...
	}
	
}
