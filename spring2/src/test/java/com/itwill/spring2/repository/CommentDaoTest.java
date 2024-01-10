package com.itwill.spring2.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.domain.Comment;

import lombok.extern.slf4j.Slf4j;

@Slf4j // log4j-slf4j2 이용한 로그 출력하기 위해서 -> Logger 객체 생성.
@ExtendWith(SpringExtension.class) // Spring JUnit 테스트를 실행하는 메인 클래스.
@ContextConfiguration(
		// 스프링 컨텍스트 설정. 
		// 지정된 위치에서 스프링 설정 파일을 로드하거나,
		// 클래스나 설정 클래스를 직접 지정하여 스프링 컨텍스트를 구성함.
		// 테스트 시에 스프링 빈(bean)을 주입하고, 의존성을 해결하기 위해 스프링 컨텍스트를 사용할 수 있도록 함.
		// 클래스 패스에 있는 application-context.xml 파일을 스프링 컨텍스트로 로드.
		locations = { "file:src/main/webapp/WEB-INF/application-context.xml" } // 스프링 컨텍스트(환경 변수) 파일의 경로(이름).
)

public class CommentDaoTest {
	
	// CommentDao 타입 객체를 주입.
	// 리포지토리 객체를 주입받음(의존성 주입:DI - Dependency Injection, 제어의 역전:IoC - Inversion of Control)
	@Autowired private CommentDao commentDao;
	
//	@Test
	public void testSelectCommentCounts() {
		Long counts = commentDao.selectCommentCounts(1);
		log.debug("counts={}", counts);
	}
	
	@Test
	public void testUpdate() {
		Comment comment = Comment.builder()
				.id(6L)
				.ctext("댓글 업데이터 unit test")
				.build();
		
		int result = commentDao.update(comment);
		
		Assertions.assertEquals(1, result);
		
	}
	
//	@Test
	public void testDeleteByPostId() {
		
		int result = commentDao.deleteByPostId(1);
		log.debug("result={}", result);
		
	}
	
//	@Test
	public void testDeleteById() {
		
		int result = commentDao.deleteById(5);
		Assertions.assertEquals(1, result);
		
	}
	
//	@Test
	public void testInsert() {
		Comment comment = Comment.builder()
				.postid(1L)
				.writer("guest")
				.ctext("MyBatis-Spring 댓글 입력!")
				.build();
		
		int result = commentDao.insert(comment);
		Assertions.assertEquals(1, result);
		
	}
	
//	@Test
	public void testSelectByPostId() {
		Assertions.assertNotNull(commentDao);
		
		List<Comment> list = commentDao.selectByPostId(1);
		for (Comment c : list) {
			log.debug(c.toString());
		}
	}
	
	
	
	
	
}
