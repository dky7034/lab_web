package com.itwill.spring2.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.datasource.HikariDataSourceTest;
import com.itwill.spring2.domain.Post;
import com.itwill.spring2.dto.post.PostSearchDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j // log4j-slf4j2 이용한 로그 출력하기 위해서 -> Logger 객체 생성.
@ExtendWith(SpringExtension.class) // Spring JUnit 테스트를 실행하는 메인 클래스.
@ContextConfiguration( 
		// 스프링 컨텍스트 설정. 
		// 지정된 위치에서 스프링 설정 파일을 로드하거나,
		// 클래스나 설정 클래스를 직접 지정하여 스프링 컨텍스트를 구성함.
		// 테스트 시에 스프링 빈(bean)을 주입하고, 의존성을 해결하기 위해 스프링 컨텍스트를 사용할 수 있도록 함.
		// 클래스 패스에 있는 application-context.xml 파일을 스프링 컨텍스트로 로드.
		locations = { "file:src/main/webapp/WEB-INF/application-context.xml" }) // 스프링 컨텍스트(환경 변수) 파일의 경로(이름).

public class PostDaoTest {
	
	// PostDao 타입 객체를 주입.
	@Autowired private PostDao postDao;
	
//	@Test -> 테스트 종료 후 주석으로 변경.
	public void selecTest() {
		Assertions.assertNotNull(postDao);
		
		List<Post> list = postDao.selectOrderByIdDesc();
		log.debug("list size={}", list.size());
		if (list.size() > 0) {
			log.debug(list.get(0).toString());
		}
		
	}
	
//	@Test -> 테스트 종료 후 주석으로 변경.
	public void selectByIdTest() {
		Post p = postDao.selectById(1); // 테이블에 존재하는 아이디로 검색했을 때. not null 기대.
		Assertions.assertNotNull(p);
		log.debug("p={}", p);
		
		p = postDao.selectById(1000); // 테이블에 존재하지 않는 아이디로 검색했을 때. null 기대.
		Assertions.assertNull(p);
	}
	
//	@Test -> 테스트 종료 후 주석으로 변경.
	public void insertTest() {
		Post post = Post.builder() // builder pattern 사용.
				.title("테스트 MyBatis")
				.content("11/29 MyBatis 테스트")
				.author("admin")
				.build();
		int result = postDao.insert(post);
		Assertions.assertEquals(1, result); // insert 성공 시 1이 리턴됨을 기대. (삽입된 행의 개수가 1)
	}
	
//	@Test -> 테스트 종료 후 주석으로 변경.
	public void updateTest() {
		// 수정할 포스트 내용
		Post post = Post.builder()
				.id(62L)
				.title("update MyBatis")
				.content("MyBatis update test")
				.build();
		int result = postDao.update(post); // 아이디가 존재하는 경우 업데이트 성공
		Assertions.assertEquals(1, result); // update 성공 시 1이 리턴됨을 기대. (업데이트한 행의 개수가 1)
		
		post = Post.builder().id(1_000L).title("").content("").build();
		result = postDao.update(post); // 아이디가 존재하지 않는 경우 업데이트한 행의 개수가 0
		Assertions.assertEquals(0, result);
	}
	
//	@Test
	public void deleteTest() {
		int result = postDao.delete(61); // 아이디가 존재하는 경우 
		Assertions.assertEquals(1, result);
		
		result = postDao.delete(1000); // 아이디가 존재하지 않는 경우
		Assertions.assertEquals(0, result);
		
	}
	
    @Test
    public void searchTest() {
        PostSearchDto dto = new PostSearchDto();
        dto.setCategory("a");
        dto.setKeyword("tE");
        
        postDao.search(dto);
    }
}
