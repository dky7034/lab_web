package com.itwill.jsp2.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceUtil { //-> 오라클DB와 물리적으로 연결(커넥션)을 맺는 클래스.
	
	// singleton 구현:
	private static DataSourceUtil instance = null;
	
	// HikariDataSource: HikariCP에서 제공하는 데이터 소스 클래스.
	// 자바 어플리케이션에서 DB와의 연결을 관리하기 위해 사용.
	// 여러 DB 연결을 효율적으로 관리하고자 할 때, 커넥션 풀을 사용하는 것이 좋음.
	// 커넥션 풀은 미리 일정 수의 여러 데이터베이스 연결을 만들어 놓고,
	// 필요할 때마다 그 연결을 빌려주고 반납받는 방식으로 동작함.
	// HikariDataSource를 사용하면 HikariCP가 이러한 커넥션 풀을 손쉽게 구현해줌.
	// 주요 설정은 HikariConfig 객체를 통해 이루어짐.
	private HikariDataSource ds;
	
	private DataSourceUtil() {
		HikariConfig config = new HikariConfig(); // HikariCP 환경 설정 객체
		// 커넥션 풀 환경 설정
		config.setDriverClassName("oracle.jdbc.OracleDriver"); // 사용할 JDBC 드라이버 클래스 설정.
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe"); // DB에 연결할 URL 설정.
		config.setUsername("scott"); // DB 사용자 이름 설정.
		config.setPassword("tiger"); // DB 비밀번호 설정.
		
		// 데이터 소스 객체 생성
		ds = new HikariDataSource(config); //-> DB와 물리적인 커넥션 생성.
	}
	
	public static DataSourceUtil getInstance() {
		if (instance == null) {
			instance = new DataSourceUtil();
		}
		
		return instance;
	}
	
	public HikariDataSource getDataSource() {
		return ds;
	}
	
}
