package com.itwill.jps2.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.domain.User;

public class UserTest {
	private static final Logger log = LoggerFactory.getLogger(UserTest.class);
	
	@Test
	public void test() {
		// new User(null, "admin", "admin", "admin", null); -> 생성자 호출 방식 사용.
		
		// builder 패턴을 적용한 객체 생성 테스트
		User admin = User.builder().userid("admin").password("1234").build();
		Assertions.assertEquals("admin", admin.getUserid());
		Assertions.assertEquals("1234", admin.getPassword());
		log.debug("admin={}", admin);
	}
}
