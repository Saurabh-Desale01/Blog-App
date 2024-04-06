package com.blogapp.blog_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapp.blog_app.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByBlogPostId(long postId);
}
