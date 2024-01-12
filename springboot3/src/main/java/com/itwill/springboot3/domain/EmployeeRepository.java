package com.itwill.springboot3.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	// JPA query method 작성 방법:
	
	// 부서 번호가 일치하는 모든 직원들의 정보를 검색
	// select * from employees where department_id = ?
	List<Employee> findByDepartmentId(Integer id);
	//ㄴ Department: Employee Entity 참조 / Id: Department Entity 의 변수명
	
	// 부서 이름이 일치하는 (대소문자는 구분하지 않는) 모든 직원들의 정보를 검색.
	List<Employee> findByDepartmentDepartmentNameIgnoreCase(String departmentName);
	
}