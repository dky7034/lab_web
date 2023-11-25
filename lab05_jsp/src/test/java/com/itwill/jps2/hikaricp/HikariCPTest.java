package com.itwill.jps2.hikaricp;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// HikariCP(히카리CP)를 사용하여 데이터베이스와의 연결을 관리하는 간단한 테스트를 수행하는 Java 클래스.
// HikariCP 의존성 테스트
public class HikariCPTest {
	private static final Logger log = LoggerFactory.getLogger(HikariCPTest.class);
	
	@Test // Test 애너테이션.
	public void test() throws SQLException {
		// HikariCP(커넥션 풀) 환경 설정을 위한 객체 생성:
		HikariConfig config = new HikariConfig();
		
		// HikariCP 환경 설정:
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		config.setUsername("scott");
		config.setPassword("tiger");
		
		// 환경 설정 내용이 적용된 커넥션 풀 객체(DataSource)를 생성:
		HikariDataSource ds = new HikariDataSource(config);
		
		// 커넥션 풀(데이터 소스) 객체는 null이 아니어야 함.
		Assertions.assertNotNull(ds); //-> ds가 null이 아니어야 테스트 성공.
		log.debug("ds={}", ds);
		
		// 커넥션 풀(데이터 소스)에서 커넥션 객체를 빌려옴.
		Connection conn = ds.getConnection();
		
		// 커넥션 객체는 null이 아니어야 함.
		Assertions.assertNotNull(conn);
		log.debug("conn={}", conn);
		
		// 사용이 끝난 커넥션 객체는 풀에 반환.
		// close: DB 서버와의 물리적인 연결을 끊는 게 아니라 커넥션 풀에 반환만 하는 것!
		conn.close(); 
		log.debug("커넥션 반환 성공");
	}
}
