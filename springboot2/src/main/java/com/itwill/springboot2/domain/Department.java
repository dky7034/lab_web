package com.itwill.springboot2.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "DEPT")
public class Department {
    
    @Id
    @Column(name = "deptno")
    private Integer id;
    
    private String dname;
    
    @Column(name = "loc")
    private String location;
    
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Employee> employees;
    // DEPT 테이블이 가지고 있지 않은 컬럼. (없어도 상관 x)
    // mappedBy = "department": EMP 테이블의 department 라는 "필드"에 의해 매핑되는 속성 설정.
    //ㄴ Employee.java 에 존재하는 필드.
}