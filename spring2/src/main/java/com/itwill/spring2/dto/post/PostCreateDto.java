package com.itwill.spring2.dto.post;

import com.itwill.spring2.domain.Post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreateDto {
	
	private String title;
	private String content;
	private String author;
	
	// PostCreateDTO 객체를 Post 모델(엔티티) 객체로 변환해서 리턴하는 메서드.
	// static일 필요가 없음. 사용자가 입력한 데이터의 타입이 Post이므로
	// 이를 DTO로 변환하는 과정이 필요함.
	public Post toEntity() {
		return Post.builder()
				.title(title)
				.content(content)
				.author(author)
				.build();
	}
}
