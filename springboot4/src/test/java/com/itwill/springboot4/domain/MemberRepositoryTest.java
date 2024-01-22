package com.itwill.springboot4.domain;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired private MemberRepository memberDao;
	@Autowired private PasswordEncoder passwordEncoder;
	
//	@Test
	public void test() {
		Assertions.assertNotNull(memberDao);
		log.info("************************");
		log.info("memberDao={}", memberDao);
		log.info("************************");
		
		Assertions.assertNotNull(passwordEncoder);
		log.info("************************");
		log.info("passwordEncoder={}", passwordEncoder);
		log.info("************************");
		
		Member m = Member.builder()
				.username("user2")
				.password(passwordEncoder.encode("2222"))
				.email("user2@itwill.com")
				.build();
		m.addRole(MemberRole.ADMIN);
		
		log.info("************************");
		log.info("save 전: {}", m);
		log.info("************************");
		
		memberDao.save(m); // insert 쿼리 실행.
		log.info("************************");
		log.info("save 후: {}", m);
		log.info("************************");
	}
	
//	@Test @Transactional
	public void testFindAll() {
		List<Member> list = memberDao.findAll();
		log.info("************************");
		list.forEach((x) -> log.info("{}, {}", x, x.getRoles())); // 전체 member를 출력.
		log.info("************************");
	}
	
	@Test
	public void testFindByUsername() {
		Optional<Member> m = memberDao.findByUsername("admin");
		log.info("************************");
		log.info("{}, {}", m, m.get().getRoles());
		log.info("************************");
	}
	
}
