package com.itwill.spring2.repository;

import java.util.List;

import com.itwill.spring2.domain.Comment;

public interface CommentDao {
	
	// 메서드명은 comment-mapper.xml에 작성된 id 와 동일하게 작성해야 함.
	List<Comment> selectByPostId(long postid); 
	int insert(Comment comment);
	int deleteById(long id);
	int deleteByPostId(long postid);
	int update(Comment comment); //-> Comment.java에 존재하는 필드에서 get하면 되므로 아규먼트를 이렇게 작성했음.
	Long selectCommentCounts(long postid);
	Comment selectById(long id);
	
}
