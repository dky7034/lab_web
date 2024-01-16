package com.itwill.springboot4.dto;

import com.itwill.springboot4.domain.Post;

import lombok.Data;

@Data
public class PostUpdateRequestDto {
	// @Data 사용 이유:
	// DispatcherServlet이 JavaBeans과 작동하는 방식과 관련:
	// DispatcherServlet이 기본 생성자 호출 -> setters 호출 -> 값 변경.

    private Long id;
    private String title;
    private String content;
    
    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }
    
}