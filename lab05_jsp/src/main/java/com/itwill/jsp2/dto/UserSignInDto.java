package com.itwill.jsp2.dto;

import com.itwill.jsp2.domain.User;
import com.itwill.jsp2.dto.UserSignUpDto.UserSignUpDtoBuilder;

public class UserSignInDto {
	private String userid;
	private String password;
	
	// TODO !!!
	
	public UserSignInDto() {}

	public UserSignInDto(String userid, String password) {
		super();
		this.userid = userid;
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserSignInDto [userid=" + userid + ", password=" + password + "]";
	}
	
	//----- builder pattern
	public static UserSignInDtoBuilder builder() { // 객체 생성 전에 호출 가능한 메서드 (static)
		return new UserSignInDtoBuilder();
	}
	
	public static class UserSignInDtoBuilder {
		private String userid;
		private String password;
		
		private UserSignInDtoBuilder() {}
		
		public UserSignInDtoBuilder userid(String userid) {
			this.userid = userid;
			return this;
		}
		
		public UserSignInDtoBuilder password(String password) {
			this.password = password;
			return this;
		}
		
		public UserSignInDto build() { // 위에서의 채워진 값을 가지고 UserSignInDto 객체가 생성됨.
			return new UserSignInDto(userid, password);
		}
	}
	
//	// UserSignInDto 타입의 객체를 User 타입 객체로 변환해서 리턴하는 메서드.
//	//-> Service 계층에서 Repository 계층의 메서드를 호출할 때 사용.
//	public User toUser() {
//		return new User(null, userid, password, password, null);
//	}
	
}
