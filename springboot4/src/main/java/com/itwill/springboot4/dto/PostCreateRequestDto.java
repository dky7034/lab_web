package com.itwill.springboot4.dto;

import com.itwill.springboot4.domain.Post;

import lombok.Data;

@Data
//-> 기본 생성자, getters & setters
// dto는 반드시 @Data로 작성해야 함!(기본 생성자, setters 필요): 자바의 동작 방식 때문...
public class PostCreateRequestDto {

    private String title;
    private String content;
    private String author;
    
    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
    
}