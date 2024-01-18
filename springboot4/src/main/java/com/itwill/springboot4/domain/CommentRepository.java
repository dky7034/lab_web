package com.itwill.springboot4.domain;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	// JPA query methods
//	List<Comment> findByPostOrderByIdDesc(Post post);
	List<Comment> findByPost(Post post, Sort sort);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE Comment cmt SET cmt.text = :newText WHERE cmt.id = :id")
	int updateCommentById(@Param("id") Long id, @Param("newText") String newText);
	
}
