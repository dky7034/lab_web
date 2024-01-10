package com.itwill.spring2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.spring2.domain.Post;
import com.itwill.spring2.dto.post.PostCreateDto;
import com.itwill.spring2.dto.post.PostListItemDto;
import com.itwill.spring2.dto.post.PostSearchDto;
import com.itwill.spring2.repository.PostDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service // 스프링 컨테이너에서 관리하는 서비스 컴포넌트.
public class PostService {
	// TODO: PostDao를 주입받음.
	@Autowired
	private PostDao postDao; // 객체 생성 권한이 Spring FrameWork로 이전됨. (의존성 주입, 제어의 역전)
	// Spring Container가 가지고 있는 빈(bean = 객체) 중에서 타입에 맞는 객체를 주입해줌.
	
	public List<PostListItemDto> read() {
		log.debug("read()");
		// TODO: postDao의 메서드를 호출해서 포스트 목록을 리턴받고, 컨트롤러에게 리턴해 줌.
		List<Post> list = postDao.selectOrderByIdDesc(); //-> DB에서 가져오는 데이터는 Post 타입이 맞음.
		log.debug("포스트 목록 개수 = {}", list.size());
		
		return list.stream()
				.map(PostListItemDto::fromEntity) //-> 람다 표현식을 간단하게 작성한 것. (조건 주의)
				// map((x) -> PostListItemDto.fromEntity(x)): 람다 표현식
				// 일종의 필터링 과정을 거치는 코드라고 생각!
				.toList();
	}
	
	public int create(PostCreateDto dto) { // 생성(삽입)된 행의 개수를 리턴하므로 int 타입 사용.
		log.debug("create(dto={})", dto);
		
		// 리포지토리(DAO) 계층의 메서드를 호출해서 테이블에 데이터를 삽입(insert).
		int result = postDao.insert(dto.toEntity());
		log.debug("create result = {}", result);
		
		return result;
	}
	
	public Post details(Long id) { // 전달받은 id 값에 해당하는 포스트를 리턴하므로 Post 타입 사용.
		log.debug("details(id={})", id);
		
		// 리포지토리 게층의 메서드를 호출해서 DB 테이블에서 해당 아이디의 포스트 상세내용을 검색.
		Post post = postDao.selectById(id);
		log.debug("{}", post);
		
		return post;
	}
	
	public int delete(long id) {
		
		// 리포지토리 계층의 메서드를 호출해서 delete SQL을 실행.
		int result = postDao.delete(id);
		
		return result;
	}
	
	public int update(Post post) {
		
		// 리포지토리 계층의 메서드를 호출해서 update SQL을 실행.
		int result = postDao.update(post);
		
		return result;
	}
	
	public List<PostListItemDto> search(PostSearchDto dto) {
		log.debug("search(dto={}", dto);
		
		List<Post> list = postDao.search(dto);
		
		return list.stream()
				.map(PostListItemDto::fromEntity)
				.toList();
	}
	
}
