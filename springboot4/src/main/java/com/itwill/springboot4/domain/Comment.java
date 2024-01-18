package com.itwill.springboot4.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Builder //-> builder만을 위하므로 접근 못하게 숨김처리...
@Getter @ToString @EqualsAndHashCode
@Entity @Table(name = "comments")
public class Comment extends BaseTimeEntity { //-> 생성 시간, 수정 시간은 상속받아서 사용!
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ToString.Exclude //-> post 객체는 toString 메서드에서 제외... 콘솔 로그 출력이 보기 좋게 간단해짐.
	@ManyToOne(fetch = FetchType.LAZY) //-> 댓글 여러 개가 하나의 포스트에 달릴 수 있으므로...
	@JoinColumn(name = "post_id") //-> 테이블과 테이블이 서로 관계(join)를 맺으므로 JoinColumn 사용.
	private Post post; //-> comment가 post와 관련이 된다...
	
	@Basic(optional = false) //-> not null
	@Column(name = "ctext") //-> 엔터티의 필드와 실제 테이블의 컬럼 이름이 다름을 명시.
	private String text;
	
	@Basic(optional = false)
	private String writer;
	
	public Comment update(String text) {
		this.text = text;
		return this;
	}
	
}
