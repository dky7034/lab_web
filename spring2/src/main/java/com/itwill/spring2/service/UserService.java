package com.itwill.spring2.service;

import org.springframework.stereotype.Service;

import com.itwill.spring2.domain.User;
import com.itwill.spring2.dto.user.UserRegisterDto;
import com.itwill.spring2.dto.user.UserSignInDto;
import com.itwill.spring2.repository.UserDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor //-> final 변수를 초기화 할 수 있는 생성자를 생성.
//-> final 키워드가 붙은 필드들을 아규먼트로 받는 생성자를 자동으로 생성하여 의존성 주입 수행.
@Service
public class UserService {
	
	private final UserDao userDao;
	
	public boolean checkUserid(String userid) { // 리턴 타입은 자유롭게 작성~
		log.debug("checkUserid(userid={})", userid);
		
		User user = userDao.selectByUserid(userid);
		if (user == null) { // USERS 테이블에 userid가 없는 경우 -> 회원가입에서 사용 가능한 아이디
			return true;
		} else { // USERS 테이블에 userid가 이미 있는 경우 -> 회원가입에서 사용 불가능한 아이디
			return false;
		}
	}
	
	public int create(UserRegisterDto dto) { // userid, password, email 만 전달받기 위한 dto
		log.debug("create(dto={}", dto);
		
		int result = userDao.insert(dto.toEntity());
		log.debug("회원가입 결과 = {}", result);
		
		return result;
	}
	
//	내 코드(로그인)
//	public User checkUseridAndPassword(String userid, String password) {
//		log.debug("checkUseridAndPassword(userid={}, password={})", userid, password);
//		
//		UserSignInDto dto = UserSignInDto.builder()
//				.userid(userid)
//				.password(password)
//				.build();
//		
//		User signedInUser = userDao.selectByUseridAndPassword(dto);
//		
//		return signedInUser;
//	}
	
	// 선생님 코드(로그인)
	public User read(UserSignInDto dto) {
		log.debug("read(dto={})", dto);
		
		// 리포지토리 메서드를 호출해서, 아이디와 비밀번호가 일치하는 사용자가 있는지 검색.
		User user = userDao.selectByUseridAndPassword(dto);
		log.debug("로그인 사용자 = {}", user);
		
		return user;
	}
}
