package com.itwill.springboot4.dto;

import lombok.Data;

@Data
public class CommentRegisterRequestDto {
	
	private Long postId; // JavaScript 변수명 / 크롬 페이로드와 동일한 이름으로...
	private String text;
	private String writer;
	
}
