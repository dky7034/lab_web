package com.itwill.springboot4.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostQuerydslTest {
	
	@Autowired private PostRepository postDao;
	
//	@Test
	public void test() {
		Assertions.assertNotNull(postDao);
		
		Post entity = postDao.searchById(18L);
		log.info("===================");
		log.info("entity={}", entity);
		log.info("===================");
	}
	
	@Test
	public void test2() {
		String[] keywords = { "TEST", "테스트", "관리자" };
		Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
		Page<Post> page = postDao.searchByKewords(keywords, pageable);
		page.forEach((p) -> log.info(p.toString()));
		
//		List<Post> list = 
//				postDao.searchByTitleOrContent("tESt");
//				postDao.searchByModifiedTime(
//						LocalDateTime.of(2024, 1, 12, 0, 0), 
//						LocalDateTime.of(2024, 1, 19, 0, 0));
//				postDao.searchByKeywordAndAuthor("테스트", "admin");
//				postDao.searchByKeywords(keywords);
		
//		list.forEach((p) -> log.info(p.toString())); 
		//-> list에서 꺼낸 Post 객체(p)를 log.info로 출력.
	}
	
}
