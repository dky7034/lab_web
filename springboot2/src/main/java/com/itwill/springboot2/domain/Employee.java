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
import lombok.ToString;

@Data
@Entity
@Table(name = "EMP") // EMP 테이블.
public class Employee {
    
    @Id // Primary Key (반드시 표시해야 함)
    @Column(name = "EMPNO")
    private Integer id; // 컬럼명: EMPNO
    
    private String ename; // 컬럼명: ENAME
    
    private String job; // 컬럼명: JOB
    
//    @Column(name = "MGR")
//    private Integer manager;
    
    @ToString.Exclude // toString 메서드에서 제외.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MGR") // 자체 조인: 직원이 다른 직원을 관리자로 둘 수 있음.
    private Employee manager; // 컬럼명: MGR
    
    private LocalDate hiredate; // 컬럼명: HIREDATE
    
    @Column(name = "SAL")
    private Double salary; // 컬럼명: SAL
    
    @Column(name = "COMM")
    private Double commission; // 컬럼명: COMM
    
//    private Integer deptno;
    
    @ToString.Exclude // toString 메서드에서 제외
    @ManyToOne(fetch = FetchType.LAZY) // FK 컬럼에 해당하는 엔터티 객체.
    @JoinColumn(name = "deptno") // EMP 테이블에서 DEPT와 join 할 수 있는 컬럼 이름.
    private Department department;

}