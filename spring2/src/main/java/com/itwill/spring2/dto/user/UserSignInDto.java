package com.itwill.spring2.dto.user;

import com.itwill.spring2.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 로그인 정보를 저장하는 DTO
@Data // getter, setter, equals, hashCode, toString 메서드가 자동으로 생성됨.
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignInDto {
	private String userid; // 폼에 작성된 name과 같도록 하면 편함.
	private String password; // 폼에 작성된 name과 같도록 하면 편함.
	
	public User toEntity() {
		return User.builder().userid(userid).password(password).build();
	}
}
