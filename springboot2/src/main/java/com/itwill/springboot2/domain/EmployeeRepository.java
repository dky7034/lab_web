//이 코드는 Spring Data JPA에서 사용되는 Repository 인터페이스를 정의하는 것입니다. 
//EmployeeRepository는 JpaRepository를 상속받고 있습니다.
//JpaRepository는 Spring Data JPA에서 제공하는 인터페이스로, 
//데이터베이스와 관련된 기본적인 CRUD(Create, Read, Update, Delete) 기능을 제공합니다. 
//Employee 엔티티와 Integer 타입의 ID를 사용하여 JpaRepository를 구현함으로써 
//Employee 엔티티에 대한 CRUD 작업을 수행할 수 있습니다.
//EmployeeRepository 인터페이스를 통해 다음과 같은 기본적인 데이터베이스 작업을 수행할 수 있습니다.
//
//- 저장된 모든 Employee 엔티티를 조회
//- 특정 ID에 해당하는 Employee 엔티티를 조회
//- 새로운 Employee 엔티티를 저장
//- 기존의 Employee 엔티티를 수정
//- 특정 ID에 해당하는 Employee 엔티티를 삭제
//
//EmployeeRepository 인터페이스를 사용하면 이러한 데이터베이스 작업을 간단하게 수행할 수 있으며, 
//직접 SQL 쿼리를 작성하지 않아도 됩니다.
//
//이러한 Spring Data JPA의 Repository 인터페이스는 데이터베이스 작업을 추상화하여 
//개발자가 간단하게 데이터베이스와 상호작용할 수 있도록 도와줍니다.
package com.itwill.springboot2.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}