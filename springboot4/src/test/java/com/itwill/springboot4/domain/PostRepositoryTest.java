package com.itwill.springboot4.domain;

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
public class PostRepositoryTest {
	
	@Autowired private PostRepository postDao; // 주입받기...
	
//	@Test
	public void testFindAll() {
		Assertions.assertNotNull(postDao);
		
		// DB 테이블에서 전체 검색 테스트.
		List<Post> list = postDao.findAll();
		list.forEach((x) -> log.info(x.toString()));
	}
	
//	@Test
	public void testSave() {
		
		// DB 테이블에 insert 테스트.
		Post entity = Post.builder()
				.title("테스트")
				.content("JPA 저장 테스트")
				.author("admin")
				.build();
		log.info("저장 전: {}", entity);
		
		postDao.save(entity);
		//-> 엔터티의 id 필드가 null인 경우 insert 쿼리를 실행.
		// 엔터티의 id 필드 값으로 검색(select)할 수 있는 레코드가 없는 경우, insert.
		
		log.info("저장 후: {}", entity);
	}
	
//	@Test
	public void testUpdate() {
		
		// PK(id)로 포스트 엔터티를 검색.
		Post entity = postDao.findById(1L).orElseThrow();
		log.info("(1) 검색: {}", entity);
		
		// 업데이트할 필드들을 수정.
		entity.update("update test", "업데이트 테스트...");
		log.info("(2) 수정: {}", entity);
		
		// 엔터티를 저장.(@Transactional 애너테이션을 사용하는 경우에는 save를 호출할 필요가 없음)
		postDao.save(entity);
		log.info("(3) update: {}", entity);
		
	}
	
//	@Test
	public void testDelete() {
		
		postDao.deleteById(1L);
		// id로 select 쿼리를 실행한 후, 엔터티가 존재할 때 delete 쿼리를 실행함.
		
	}
	
	@Test
	public void testSearch() {
		Pageable pageable = PageRequest.of(2, 5, Sort.by("id").descending());
		
//		Page<Post> list = postDao.findByTitleContainingIgnoreCase("tES", pageable); // 제목 검색
		
		Page<Post> list = postDao.findByTitleOrContent("tES", pageable); // 제목 또는 내용 검색
		
		list.forEach((x) -> log.info(x.toString()));
	}
	
}
