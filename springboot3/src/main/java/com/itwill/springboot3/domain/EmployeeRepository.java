package com.itwill.springboot3.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	// JPA query method 작성 방법:
	// Entity의 필드 이름을 사용!!!
	// 카멜 표기법 준수!!!
	
	// 부서 번호가 일치하는 모든 직원들의 정보를 검색
	// select * from employees where department_id = ?
	List<Employee> findByDepartmentId(Integer id);
	//ㄴ Department: Employee Entity 참조 / Id: Department Entity 의 변수명
	
	// 부서 이름이 일치하는 (대소문자는 구분하지 않는) 모든 직원들의 정보를 검색.
	List<Employee> findByDepartmentDepartmentNameIgnoreCase(String departmentName);
	
	// 성(lastName)이 일치하는 모든 직원들의 정보를 검색(where last_name = ?)
	List<Employee> findByNameLastName(String lastName);
	
	// lastName에 문자열이 포함되는 직원들의 정보(where last_name like ?)
	List<Employee> findByNameLastNameContaining(String lastName);
	
	// lastName에 대소문자 구분없이 특정 문자열이 포함되는 직원들의 정보
	// where upper(last_name) like upper(?)
	List<Employee> findByNameLastNameContainingIgnoreCase(String lastName);
	
	// lastName에 대소문자 구분없이 특정 문자열이 포함되고, 정렬 순서는 lastName 오름차순
	// where upper(last_name) like upper(?) order by last_name
	List<Employee> findByNameLastNameContainingIgnoreCaseOrderByNameLastName(String lastName);
	
	// 급여(salary)가 어떤 값을 초과하는 직원들의 정보(where salary > ?)
	List<Employee> findBySalaryGreaterThan(Double salary);
	
	// 급여가 어떤 값 미만인 직원들의 정보(where salary < ?)
	List<Employee> findBySalaryLessThan(Double salary);
	
	// 급여가 어떤 범위 안에 있는 직원들의 정보(where salary between ?1 and ?2)
	List<Employee> findBySalaryBetween(Double start, Double end);
	
	// 입사날짜(hireDate)가 특정 날짜 이후인 직원들의 정보(where hire_date > ?)
	List<Employee> findByHireDateAfter(LocalDate date);
	
	// 입사날짜가 특정 날짜 이전인 직원들의 정보(where hire_date < ?)
	List<Employee> findByHireDateBefore(LocalDate date);
	
	// 입사날짜가 날짜 범위에 포함되는 직원들의 정보(where hire_date between ? and ?)
	List<Employee> findByHireDateBetween(LocalDate startDate, LocalDate endDate);
	
	// 매니저가 null 인 직원들의 정보(where manager_id is null)
	List<Employee> findByManagerIdNull(); // findByManagerIdIsNull
	
	// JPQL(Java Persistence Query Language)
	// JPA에서 사용하는 객체지향 쿼리.
	// 테이블 이름과 테이블 컬럼으로 SQL을 작성하는 것이 아니라,
	// Entity 객체의 이름과 Entity 필드 이름으로 쿼리를 작성하는 문법.
	// alias(별명)을 반드시 사용해야 함.
	// 엔터티 이름과 엔터티 필드 이름들은 대소문자를 구분함!
		
	// firstName과 lastName이 일치하는 직원들의 정보.
	// @Query("select e from Employee e where e.name.firstName = ?1 and e.name.lastName = ?2")
	// List<Employee> findByName(String firstName, String lastName);
	@Query("select e from Employee e where e.name.firstName = :first and e.name.lastName = :last")
	List<Employee> findByName(@Param("first") String firstName, @Param("last") String lastName);
	
	// firstName 또는 lastName에 특정 문자열을 포함하는 직원들의 정보. 대/소문자 구분 없이.
	@Query("select e from Employee e where upper(e.name.firstName) " + 
			"like upper(concat('%', :keyword, '%')) " + 
			"or upper(e.name.lastName) like upper(concat('%', :keyword, '%'))")
	List<Employee> findByNameContaining(@Param("keyword") String keyword);
	
	// 부서 이름으로 직원 정보 검색.
	@Query("select e from Employee e " + 
			"where e.department.departmentName = :dname")
	List<Employee> findByDepartmentName(@Param("dname") String dname);
	
	// 특정 도시(예: Seattle, Toronto)에 근무하는 직원들의 정보 검색.
	@Query("select e from Employee e " + 
			"where e.department.location.address.city = ?1")
	List<Employee> findByCity(String city);
	
	@Query("select e from Employee e " +
			"where e.department.location.address.city = :city")
	List<Employee> findByLocationAddressCity(@Param("city") String city);
	
	// 특정 국가(예: Canada, United States of America)에 근무하는 직원들의 정보 검색.
	@Query("select e from Employee e " + 
			"where e.department.location.country.countryName = ?1")
	List<Employee> findByCountry(String country);
	
	@Query("select e from Employee e " + 
			"where e.department.location.country.countryName = :countryName")
	List<Employee> findByCountryName(@Param("countryName") String countryName);
	
	// 특정 국가에서 근무하는, 급여가 특정 금액 이상인, 입사일이 특정 날짜 이후인 직원 검색.
	@Query("select e from Employee e " + 
			"where e.department.location.country.countryName = :countryName " + 
			"and e.salary >= :salary " + 
			"and e.hireDate > :hireDate")
	List<Employee> findByCountryNameAndSalaryAndhirdDate(
			@Param("countryName") String countryName, 
			@Param("salary") Double salary,
			@Param("hireDate") LocalDate hireDate);
	
}