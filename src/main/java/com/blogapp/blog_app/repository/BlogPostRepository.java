package com.blogapp.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapp.blog_app.entity.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long>{

}
