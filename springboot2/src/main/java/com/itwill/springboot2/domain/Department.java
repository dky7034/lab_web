package com.itwill.springboot2.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DEPT")
public class Department {
    
    @Id
    @Column(name = "deptno")
    private Integer id;
    
	@OneToMany(fetch = FetchType.LAZY) // FK 칼럼에 해당하는 엔터티 객체.
	@JoinColumn(name = "") // DEPT 테이블에서 EMP와 join할 수 있는 컬럼 이름.
    private Employee employee;
    
    private String dname;
    
    @Column(name = "loc")
    private String location;

}