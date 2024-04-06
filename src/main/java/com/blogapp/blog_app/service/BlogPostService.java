package com.blogapp.blog_app.service;

import java.util.List;

import com.blogapp.blog_app.dto.BlogPostDto;
import com.blogapp.blog_app.dto.BlogPostResponse;

public interface BlogPostService {
	
	public BlogPostDto createPost(BlogPostDto blogPostDto);
	
	public BlogPostResponse getAllPost(int pageNo, int PageSize, String sortBy, String sortDir);
	
	public BlogPostDto getBlogPostById(long id);
	
	public BlogPostDto updateBlogPost(BlogPostDto blogPostDto, long id);
	
	public void deleteBlogPostById(long id);

}
