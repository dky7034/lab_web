package com.itwill.springboot4.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostQuerydsl { 
//-> 인터페이스는 인터페이스를 제한 없이 상속받을 수 있다는 점을 이용.
//-> PostQuerydsl과 PostQuerydslImpl을 굳이 나눈 이유이기도 함...
//-> *** JPA, Querydsl 모두 사용 가능하게 됨!!! ***
//-> PostRepository만 주입받아도 모두 사용 가능하게 만듦.
    
    // JPA Query methods
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Post> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Post> findByAuthorContainingIgnoreCase(String keyword, Pageable pageable);

    // findByTitleContainingOrContentContainingAllIgnoreCase()
    // JPQL(Java Persistence Query Language)
    @Query("select p from Post p "
            + "where upper(p.title) like upper('%' || :keyword || '%') "
            + "or upper(p.content) like upper('%' || :keyword || '%')")
    Page<Post> findByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);
    
}