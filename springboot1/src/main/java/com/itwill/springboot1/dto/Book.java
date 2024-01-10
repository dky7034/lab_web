package com.itwill.springboot1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자를 자동으로 생성.
@AllArgsConstructor // 모든 필드를 아규먼트로 갖는 생성자를 자동으로 생성.
@Builder // 모든 필드를 아규먼트로 갖는 생성자를 이용하여 빌드를 생성하는 애너테이션. 빌더 패턴을 통해 객체 생성.
@Data // 모든 필드에 대한 getter, setter, equals, hashCode, toString 등의 메서드를 자동 생성.
public class Book {
	
	private int id;
	private String title;
	private Author author;
	
}
