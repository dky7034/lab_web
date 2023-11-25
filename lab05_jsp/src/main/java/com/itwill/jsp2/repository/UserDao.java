package com.itwill.jsp2.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itwill.jsp2.datasource.DataSourceUtil;
import com.itwill.jsp2.domain.User;
import com.itwill.jsp2.dto.UserSignInDto;
import com.zaxxer.hikari.HikariDataSource;

public class UserDao {
	private static final Logger log = LoggerFactory.getLogger(UserDao.class); // log 사용 위함.
	
	private final HikariDataSource ds = DataSourceUtil.getInstance().getDataSource(); // 히카리데이터소스를 리턴받는 과정.
	
	// singleton.
	private static UserDao instance = null;
	
	private UserDao() {}
	
	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		
		return instance;
	}
	
	// 회원 가입 SQL
	private static final String SQL_SIGN_UP = 
			"insert into USERS (USERID, PASSWORD, EMAIL) values (?, ?, ?)"; // id는 열 시퀀스, points는 default=0 이므로 따로 값을 넣지 않아도 됨.
	
	// 회원 가입 메서드
	public int insert(User user) {
		log.debug("insert(user={})", user);
		
		int result = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ds.getConnection(); // 데이터 소스에서 커넥션 객체를 빌려옴.
			stmt = conn.prepareStatement(SQL_SIGN_UP);
			log.debug(SQL_SIGN_UP);
			stmt.setString(1, user.getUserid()); // 1번 물음표에는 userid 를 넣음.
			stmt.setString(2, user.getPassword()); // 2번 물음표에는 password 를 넣음.
			stmt.setString(3, user.getEmail()); // 3번 물음표에는 email 을 넣음.
			
			result = stmt.executeUpdate(); // db에서 insert한 행의 개수를 리턴 (1 리턴됨)
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn,stmt);
		}
		
		return result;
	}
	
	// 로그인 체크 SQL
	private static final String SQL_SIGN_IN = 
			"select * from USERS where USERID = ? and PASSWORD = ?";
	
	// 로그인 메서드
	// userid와 password가 일치하면 User 객체를, 일치하지 않으면 null을 리턴.
	public User selectByUseridAndPassword(UserSignInDto dto) {
		log.debug("selectByUseridAndPassword(dto={})", dto);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = ds.getConnection(); // 데이터 소스에서 커넥션 객체를 빌려옴.
			stmt = conn.prepareStatement(SQL_SIGN_IN);
			log.debug(SQL_SIGN_IN);
			
			stmt.setString(1, dto.getUserid());
			stmt.setString(2, dto.getPassword());
			
			rs = stmt.executeQuery();
			if (rs.next()) { // ResultSet에 레코드가 있으면 (rs.next() = true 인 경우)
				// SQL 쿼리와 일치하는 DB 하나만 추출하면 되므로 while이 아닌 if를 사용함.
				user = generateUserFromRS(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		
		return user;
	}
	
	// 사용자가 포스트를 작성했을 때 포인트를 지급:
	private static final String SQL_UPDATE_POINT = 
			"update USERS set POINTS = POINTS + ? where USERID = ?";
	
	public int updatePoints(int point, String userid) {
		log.debug("updatedPoints(point={}, userid={}", point, userid);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(SQL_UPDATE_POINT);
			log.debug(SQL_UPDATE_POINT);
			
			stmt.setInt(1, point);
			stmt.setString(2, userid);
			
			result = stmt.executeUpdate(); // insert, delete, update 관련 구문에서는 반영된 레코드의 건수를 반환함(int)
			log.debug("포인트 업데이트 result = {}", result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt);
		}
		
		return result;
	}
	
	// User 객체를 생성하는 메서드.
	private User generateUserFromRS(ResultSet rs) throws SQLException {
		Long id = rs.getLong("ID");
		String userid = rs.getString("userid");
		String password = rs.getString("PASSWORD");
		String email = rs.getString("EMAIL");
		Long points = rs.getLong("POINTS");
//		return new User(id, SQL_SIGN_IN, SQL_SIGN_UP, SQL_SIGN_IN, points); 
		return User.builder()
				.id(id).userid(userid).password(password).email(email).points(points)
				.build();
	}
	
	// 리소스를 반환하는 메서드 1 (아규먼트 3개)
	private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 리소스를 반환하는 메서드 2 (아규먼트 2개)
	private void closeResources(Connection conn, Statement stmt) {
		closeResources(conn, stmt, null);
	}
	
}
