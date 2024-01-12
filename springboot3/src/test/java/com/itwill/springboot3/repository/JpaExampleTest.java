package com.itwill.springboot3.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.itwill.springboot3.domain.Address;
import com.itwill.springboot3.domain.Country;
import com.itwill.springboot3.domain.Department;
import com.itwill.springboot3.domain.Employee;
import com.itwill.springboot3.domain.EmployeeName;
import com.itwill.springboot3.domain.EmployeeRepository;
import com.itwill.springboot3.domain.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class JpaExampleTest {
	
	@Autowired private EmployeeRepository empDao;
	
//	@Test
	public void test1() {
		// lastName이 King인 직원(들)의 정보 검색.
		// Employee 타입 객체 emp 생성.
		Employee emp = new Employee();
		
		// EmployeeName 타입 객체 생성.
		EmployeeName name = new EmployeeName();
		
		// EmployeeName 타입 객체 name의 lastName을 King으로 설정.
		name.setLastName("King");
		
		// Employee 타입 객체 emp의 name 속성에 name 객체를 설정. (lastName만 설정됨)
		emp.setName(name);
		
		// emp 객체를 사용하여 Example 생성.
		Example<Employee> example = Example.of(emp);
		
		// Example을 사용하여 lastName이 "King"인 직원(들)의 정보를 검색.
		List<Employee> list = empDao.findAll(example);
		
		list.forEach((e) -> log.info(e.toString()));
	}
	
//	@Test
	public void test2() {
		// 부서 이름이 "IT"인 직원들의 정보를 Example을 사용해서 검색
		// Department 타입 객체 dept 생성.
		Department dept = new Department();
		
		// 부서 이름을 IT로 설정...
		dept.setDepartmentName("IT");
		
		// Employee 타입 객체 emp 생성.
		Employee emp = new Employee();
		
		// IT 부서에 근무하는 직원...
		emp.setDepartment(dept);
		
		// emp 객체를 사용하여 Example 생성.
		Example<Employee> example = Example.of(emp);
		
		// 페이징 처리... (List 타입X, Page 타입O)
		Pageable pageable = PageRequest.of(1, 3, Sort.by("id"));
		
		Page<Employee> list = empDao.findAll(example, pageable);
		list.forEach((e) -> log.info(e.toString()));
		log.info("number={}, # of elements={}, # of total={}", 
				list.getNumber(), 
				list.getNumberOfElements(),
				list.getTotalElements());
	}
	
//	@Test
	public void test3() {
		// 특정 도시에서 근무하는 직원들.
		// Address 타입 객체 address 생성.
		Address address = new Address();
		address.setCity("Seattle");
		
		// Location 타입 객체 loc 생성.
		Location loc = new Location();
		loc.setAddress(address);
		
		// Department 타입 객체 dept 생성.
		Department dept = new Department();
		dept.setLocation(loc);
		
		// Employee 타입 객체 emp 생성.
		Employee emp = new Employee();
		// Seattle 에서 근무하는 직원...
		emp.setDepartment(dept);
		// emp 객체를 사용하여 Example 생성.
		Example<Employee> example = Example.of(emp);
		
		// 페이징 처리... (List 타입X, Page 타입O)
		Pageable pageable = PageRequest.of(0, 20, Sort.by("id"));
		
		Page<Employee> list = empDao.findAll(example, pageable);
		list.forEach((e) -> log.info(e.toString()));
		log.info("number={}, # of elements={}, # of total={}", 
				list.getNumber(), 
				list.getNumberOfElements(),
				list.getTotalElements());
	}
	
	@Test
	public void test4() {
		// 특정 나라에서 근무하는 직원들.
		// Country 타입 객체 country 생성.
		Country country = new Country();
		country.setCountryName("United States of America");
		
		// Location 타입 객체 loc 생성.
		Location loc = new Location();
		loc.setCountry(country);
		
		// Department 타입 객체 dept 생성.
		Department dept = new Department();
		dept.setLocation(loc);
		
		// Employee 타입 객체 emp 생성.
		Employee emp = new Employee();
		// United States of America에서 근무하는 직원...
		emp.setDepartment(dept);
		
		// emp 객체를 사용하여 Example 생성.
		Example<Employee> example = Example.of(emp);
		
		// Example을 사용하여 countryName이 "United States of America"인 직원(들)의 정보를 검색.
		List<Employee> list = empDao.findAll(example);
		
		list.forEach((e) -> log.info(e.toString()));
		
	}
	
}
