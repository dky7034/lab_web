package com.itwill.springboot3.repository;

import java.time.LocalDate;
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
	
	@Test
	public void test() {
		
//		Assertions.assertNotNull(empDao);
//		
//		List<Employee> list = empDao.findByDepartmentId(90);
//		for (Employee e : list) {
//			log.info(e.toString()); // log: String 타입만 가지므로 toString 사용.
//		}
//	}
	
	
//	List<Employee> list = empDao.findByDepartmentDepartmentNameIgnoreCase("IT");
	
//	List<Employee> list = empDao.findByNameLastName("King");
	
//	List<Employee> list = empDao.findByNameLastNameContaining("ing");
	
//	List<Employee> list = empDao.findByNameLastNameContainingIgnoreCase("iNg");
	
//	List<Employee> list = empDao.findByNameLastNameContainingIgnoreCaseOrderByNameLastName("Ing");
	
//	List<Employee> list = empDao.findBySalaryGreaterThan(10000.);
	
//	List<Employee> list = empDao.findBySalaryLessThan(2_500.);
	
//	List<Employee> list = empDao.findBySalaryBetween(9_000., 10_000.);
		
//	List<Employee> list = empDao.findByHireDateAfter(LocalDate.of(2007, 1, 1));
	
//	List<Employee> list = empDao.findByHireDateBefore(LocalDate.of(2003, 1, 1));
		
//	List<Employee> list = empDao.findByHireDateBetween(
//			LocalDate.of(2007, 1, 1), LocalDate.of(2007, 12, 31));
	
//	List<Employee> list = empDao.findByManagerIdNull();
	
//	List<Employee> list = empDao.findByName("Steven", "King");
		
//	List<Employee> list = empDao.findByNameContaining("ee");
		
//	List<Employee> list = empDao.findByDepartmentName("Sales");
	
//	List<Employee> list = empDao.findByCity("Roma");

//	List<Employee> list = empDao.findByLocationAddressCity("London");
	
//	List<Employee> list = empDao.findByCountry("Canada");
	
//	List<Employee> list = empDao.findByCountryName("Canada");
	
//	List<Employee> list = empDao.findByCountryName("Mexico");
	
	List<Employee> list = empDao.findByCountryNameAndSalaryAndhirdDate(
			"United States of America", 10_000., LocalDate.of(2003, 1, 1));
	
	list.forEach((emp) -> log.info(emp.toString()));
	
	}
	
}
