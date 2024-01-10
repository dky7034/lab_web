package lab_spring01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j // log4j-slf4j2 이용한 로그 출력하기 위해서 -> Logger 객체 생성.
public class LogTest {
	private static final Logger log = LoggerFactory.getLogger(LogTest.class);

	@Test
	public void test() {
		Assertions.assertNotNull(log);
		log.info("test() - Lombok");
	}

}
