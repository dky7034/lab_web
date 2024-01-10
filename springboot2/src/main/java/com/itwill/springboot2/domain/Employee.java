package com.itwill.springboot2.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "EMP")
public class Employee {
    
    @Id
    @Column(name = "EMPNO")
    private Integer id;
    
    private String ename;
    
    private String job;
    
    @Column(name = "MGR")
    private Integer manager;
    
    private LocalDate hiredate;
    
    @Column(name = "SAL")
    private Double salary;
    
    @Column(name = "COMM")
    private Double commission;
    
//    private Integer deptno;
    
    @ManyToOne(fetch = FetchType.LAZY) // FK 컬럼에 해당하는 엔터티 객체.
    @JoinColumn(name = "deptno") // EMP 테이블에서 DEPT와 join할 수 있는 컬럼 이름.
    private Department department;

}