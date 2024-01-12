package com.itwill.springboot3.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.domain.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class JpaTest {
	
	@Autowired private EmployeeRepository empDao;
	
//	@Test
//	public void test() {
//		Assertions.assertNotNull(empDao);
//		
//		List<Employee> list = empDao.findByDepartmentId(90);
//		for (Employee e : list) {
//			log.info(e.toString()); // log: String 타입만 가지므로 toString 사용.
//		}
//	}
	
	
	List<Employee> list = empDao.findByDepartmentDepartmentNameIgnoreCase("IT");
	list.
	
}
