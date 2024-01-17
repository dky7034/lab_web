package com.itwill.springboot4.domain;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CommentRepositoryTest {
	
	@Autowired private CommentRepository commentDao; //-> comment 리포지토리 객체 주입받기...
	@Autowired private PostRepository postDao; //-> post 리포지토리 객체 주입받기...
	
//	@Test
	public void testSave() {
		Post post = postDao.findById(3L).orElseThrow();
		Comment entity = Comment.builder()
				.post(post)
				.text("댓글 insert 테스트2")
				.writer("admin")
				.build();
				// 시간은 BaseTimeEntity에 의해 자동 생성됨...
		log.info("save 전: {}, {}", entity, entity.getCreatedTime());
		
		commentDao.save(entity);
		
		log.info("save 후: {}, {}", entity, entity.getCreatedTime());
	}
	
	@Test
	public void testReadyByPostId() {
		Sort sort = Sort.by("id").descending(); // id(PK) 컬럼 내림차순 정렬
		List<Comment> list = commentDao.findByPostId(3L, sort);
		
		list.forEach((x) -> log.info(x.toString()));
	}
	
}
