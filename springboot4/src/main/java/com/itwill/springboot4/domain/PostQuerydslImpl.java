package com.itwill.springboot4.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostQuerydslImpl extends QuerydslRepositorySupport implements PostQuerydsl {
	
	public PostQuerydslImpl() {
		super(Post.class); 
		//-> 상위클래스 QuerydslRepositorySupport는 기본 생성자를 갖고 있지 않기 때문에
		// 아규먼트를 갖는 생성자를 명시적으로 호출해야만 함.
		// QuerydslRepositorySupport 생성자의 아규먼트는 도메인 클래스(엔터티 클래스).
		
	}

	@Override
	public Post searchById(Long id) {
		// id(PK)로 포스트 1개를 검색하는 기능.
		// select p.* from posts p where p.id = ?
		log.info("searchById(id={})", id);
		
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post); // select p from Post p
		query.where(post.id.eq(id)); // where p.id = ?
		Post entity = query.fetchOne();
		
		return entity;
	}

	@Override
	public List<Post> searchByTitleOrContent(String keyword) {
		log.info("searchByTitleOrContent(keyword={})", keyword);
		
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post); // select p from Post p
		//-> 어떤 엔터티를 가지고서 JPQLQuery를 만들 것인가? -> Post 엔터티로 만듦.
		query.where(
				post.title.containsIgnoreCase(keyword)
				.or(post.content.containsIgnoreCase(keyword))
		); // where p.title like ? or p.content like? (대략 이렇게 만들어짐)
		query.orderBy(post.id.desc()); // order by p.id desc
		
		return query.fetch(); // 쿼리 실행(fetch).
	}

	@Override
	public List<Post> searchByModifiedTime(LocalDateTime from, LocalDateTime to) {
		log.info("searchByModifiedTime(from={}, to={}", from, to);
		
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post);
		query.where(post.modifiedTime.between(from, to));
		query.orderBy(post.modifiedTime.desc());
		
		return query.fetch(); // 쿼리 실행(fetch).
	}

	@Override
	public List<Post> searchByKeywordAndAuthor(String keyword, String author) {
		log.info("searchByKeywordAndAuthor(keyword={}, author={})", keyword, author);
		
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post);
		BooleanBuilder builder = new BooleanBuilder(); // BooleanBuilder: 조건식을 만들어줌.
		builder.and(post.author.eq(author));
		builder.and(
				post.title.containsIgnoreCase(keyword)
				.or(post.content.containsIgnoreCase(keyword))
		);
		query.where(builder);
		
		return query.fetch();
	}

	@Override
	public List<Post> searchByKeywords(String[] keywords) {
		log.info("searchByKeywords(keywords={})", Arrays.asList(keywords)); // 문자열 배열을 리스트로 변환하여 출력.
		
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post);
		BooleanBuilder builder = new BooleanBuilder();
		for (String key : keywords) {
			builder.or(
					post.title.containsIgnoreCase(key)
					.or(post.content.containsIgnoreCase(key))
			);
		}
		query.where(builder);
		query.orderBy(post.id.desc());
		
		return query.fetch();
	}

	@Override
	public Page<Post> searchByKewords(String[] keywords, Pageable pageable) {
		log.info("searchByKewords(keywords={}, pageable={})", Arrays.asList(keywords), pageable);
		
		QPost post = QPost.post;
		JPQLQuery<Post> query = from(post); // select ... from
		BooleanBuilder builder = new BooleanBuilder();
		for (String key : keywords) {
			builder.or(
					post.title.containsIgnoreCase(key)
					.or(post.content.containsIgnoreCase(key))
			);
		}
		query.where(builder); // where ...
		
		// 페이징 & 정렬 적용
		getQuerydsl().applyPagination(pageable, query);
		
		// 한 페이지에 표시될 데이터
		List<Post> list = query.fetch();
		// 전체 원소 개수
		Long total = query.fetchCount();
		// Page(T) 타입 객체를 생성
		Page<Post> page = new PageImpl<Post>(list, pageable, total);
		//-> page 객체는 3개의 정보를 필요로 함.
		
		return page;
	}
	
}
