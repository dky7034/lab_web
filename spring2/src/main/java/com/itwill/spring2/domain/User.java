package com.itwill.spring2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 데이터베이스의 USERS 테이블의 모델(엔티티).
@Data // getter, setter, equals, hashCode, toString 메서드가 자동으로 생성
@NoArgsConstructor // 기본 생성자를 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동 생성
@Builder
public class User {
	private Long id; // PK - 오라클 시퀀스(sequence)
	private String userid; // 로그인 아이디(unique, not null)
	private String password; // 비밀번호(not null)
	private String email; // 이메일(not null)
	private Long points;
	
}
