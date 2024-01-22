package com.itwill.springboot4.domain;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor // 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Builder // TODO: allargs와 builder를 왜 사용하는가...?
@Getter // 값을 읽어오기 위함
@ToString // 로그의 편의성을 위함
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false) // TODO: 이게 뭐지...?
@Entity 
@Table(name = "members") //-> 클래스명이 실제 DB의 테이블 이름과 다름을 알려주기 위함. (name = "실제 테이블명")
public class Member extends BaseTimeEntity {
	
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@EqualsAndHashCode.Include // username 필드 만으로 equals 비교를 하기 위해서. TODO: 이건 뭘까...?
	@NaturalId // unique
	@Basic(optional = false) // not null
	@Column(updatable = false) // update 쿼리의 SET 절에서 제외. (업데이트를 하지 않겠다)
	private String username;
	
	@Basic(optional = false)
	private String password;
	
	@Basic(optional = false)
	private String email;
	
	@Builder.Default // Builder에서도 null이 아닌 Set<> 객체를 생성하기 위해서. (NullPointerException 에러 발생 방지)
	@ToString.Exclude // ToString에서 제외.
	@ElementCollection(fetch = FetchType.LAZY) // 연관된 별도의 테이블을 사용.
	@Enumerated(EnumType.STRING) // DB 테이블에 저장할 때 상수 이름(문자열)을 사용.
	private Set<MemberRole> roles = new HashSet<>();
	
	public Member addRole(MemberRole role) {
		roles.add(role);
		return this;
	}
	
	public Member clearRoles() {
        roles.clear();
        return this;
    }
	
}
