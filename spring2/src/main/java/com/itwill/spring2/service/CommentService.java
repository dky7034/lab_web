package com.itwill.spring2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.spring2.domain.Comment;
import com.itwill.spring2.dto.comment.CommentListItemDto;
import com.itwill.spring2.dto.comment.CommentRegisterDto;
import com.itwill.spring2.dto.comment.CommentUpdateDto;
import com.itwill.spring2.repository.CommentDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//-> final 키워드가 붙은 필드들을 아규먼트로 받는 생성자를 자동으로 생성하여 의존성 주입 수행.
@Service
public class CommentService {
	
	private final CommentDao commentDao; // 생성자에 의한 의존성 주입. CommentDao의 메서드를 호출하기 위함.
	
	public int create(CommentRegisterDto dto) {
		log.debug("create(dto={})", dto);
		
		// 리포지토리 계층의 메서드를 호출해서 COMMENTS 테이블에 새 데이터를 삽입(insert).
		// 변환된 엔티티 객체를 아규먼트로 전달하여, repository(dao) 계층의 insert 메서드 호출.
		int result = commentDao.insert(dto.toEntity());
		log.debug("댓글 등록 결과 = {}", result);
		
		
		return result;
	}
	
	public List<CommentListItemDto> read(long postId) {
		log.debug("read(postId={}", postId);
		
		// 리포지토리 계층의 메서드를 호출해서 데이터베이스 테이블 검색(select)
		List<Comment> list = commentDao.selectByPostId(postId);
		log.debug("댓글 개수 = {}", list.size());
		
		return list.stream()
				.map(CommentListItemDto::fromEntity) // (x) -> CommentListItemDto.fromEntity(x)
				.toList();
	}
	
	public int delete(long id) {
		log.debug("delete(id={})", id);
		
		// 리포지토리 계층의 메서드를 호출해서 COMMENTS 테이블에서 댓글 1개를 삭제.
		int result = commentDao.deleteById(id);
		log.debug("댓글 삭제 결과 = {}", result);
		
		return result;
	}
	
	public CommentListItemDto readById(long id) {
		log.debug("readById(id={}}", id);
		
		// 리포지토리 계층의 메서드를 호출해서 COMMENTS 테이블에서 아이디로 검색.
		Comment comment = commentDao.selectById(id);
		log.debug(comment.toString());
		
		return CommentListItemDto.fromEntity(comment);
	}
	
	public int update(CommentUpdateDto dto) {
		log.debug("update(dto={})", dto);
		
		// 리포지토리 계층의 메서드를 호출해서 COMMENTS 테이블에서 댓글 1개를 업데이트.
		int result = commentDao.update(dto.toEntity());
		log.debug("댓글 업데이트 결과 = {}", result);
		
		return result;
		
	}
	
}
