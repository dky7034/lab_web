package com.itwill.spring2.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.itwill.spring2.jdbc.JdbcTest;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j // log4j-slf4j2 이용한 로그 출력하기 위해서 -> Logger 객체 생성.
@ExtendWith(SpringExtension.class) // Spring JUnit 테스트를 실행하는 메인 클래스.
@ContextConfiguration(
		locations = { "file:src/main/webapp/WEB-INF/application-context.xml" }
) // 스프링 컨텍스트(환경 변수) 파일의 경로(이름).

public class HikariDataSourceTest {
	/*
	 * 의존성 주입 (DI: dependency injection), 제어의 역전 (IoC: Inversion of Control):
	 * 스프링 컨테이너에서 객체 Bean을 먼저 생성해두고 생성한 객체를 지정한 객체에 주입하는 방식을 "의존성 주입"이라고 함.
	 * 전통적인 자바 개발 방법에서는 객체를 사용하는 곳에서 객체를 생성하고, 그 기능을 이용함.
	 * 스프링 프레임워크에서는 스프링 컨테이너가 필요한 객체를 미리 메모리에 생성해 두고,
	 * 객체를 필요로 하는 곳에서는 변수 선언과 애너테이션만 사용하면
	 * 스프링 컨테이너가 생성/관리하고 있는 빈(bean = 객체)을 필요한 곳에 주입해 줌.
	 * 필요한 객체(빈)를 내가 생성하는 것이 아니라(제어가 역전) 스프링 프레임워크가 생성/관리하고 있는 빈에서 주입받는(의존성 주입) 방식.
	 * application-context.xml 파일에 선언된 bean 들을 스프링 컨테이너가 생성/관리함.
	 */
	
	@Autowired // 스프링 컨테이너가 생성/관리하는 빈(bean)을 변수에 자동 할당(주입). 자동 종속성 주입에 사용되는 스프링 프레임워크의 주석.
	@Qualifier("hikariConfig")
	private HikariConfig config; // (-> 누가 생성자를 호출하고 값을 생성하였는가 -> 스프링 프레임워크가 함)
	/*
	 * HikariConfig: super type
	 * |__ HikariDataSource: sub type
	 * 다형성 때문에 HikariConfig 타입으로 선언한 변수에는
	 * HikariConfig 타입 객체와 HikariDataSource 타입 객체를 모두 주입할 수 있음.
	 * 컨텍스트 파일에서 설정한 id 값으로 특정 빈(bean)을 주입받을 때 @Qualifier("id")를 사용.
	 */
	// HikariConfig 부모 타입 - HikariDataSource 자식 타입 -> 상속 관계로 인한 에러 발생 -> @Qualifier("id") 작성.
	@Autowired
	private HikariDataSource ds;
	
	@Autowired
	private SqlSessionFactoryBean session;
	
	@Test
	public void test() throws SQLException {
		Assertions.assertNotNull(config);
		log.debug("config={}", config);
		
		Assertions.assertNotNull(ds);
		log.debug("ds={}", ds);
		
		Assertions.assertNotNull(session);
		log.debug("session={}", session);
		
		Connection conn = ds.getConnection(); // 데이터소스(커넥션 풀)에서 커넥션을 빌려옴.
		Assertions.assertNotNull(conn);
		log.debug("conn={}", conn);
		
		conn.close(); // 사용했던 커넥션 객체를 풀에 반환.
		log.debug("커넥션 반환 성공");
	}
	
}

