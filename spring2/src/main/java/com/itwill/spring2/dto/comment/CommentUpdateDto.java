package com.itwill.spring2.dto.comment;

import com.itwill.spring2.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateDto { //-> comment-mapper.xml SQL문장을 보고 필드를 설정.
	private Long id;
	private String ctext;
	
	// CommentUpdateDto 타입 객체를 모델(엔티티) Comment 객체로 변환해서 리턴.
	public Comment toEntity() {
		return Comment.builder()
				.id(id)
				.ctext(ctext)
				.build();
	}
}
