package com.blogapp.blog_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.blog_app.dto.BlogPostDto;
import com.blogapp.blog_app.dto.BlogPostResponse;
import com.blogapp.blog_app.service.BlogPostService;
import com.blogapp.blog_app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class BlogPostController {
	
	@Autowired
	private BlogPostService blogPostService;
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/post")
	public ResponseEntity<BlogPostDto> createPost(@Valid @RequestBody BlogPostDto blogPostDto){
		
		return new ResponseEntity<>(blogPostService.createPost(blogPostDto), HttpStatus.CREATED);
		
	}
	
	@PreAuthorize("hasRole(T(com.blogapp.blog_app.entity.Role).ADMIN)")
	@GetMapping("/get")
	public BlogPostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
			){
		return blogPostService.getAllPost(pageNo, pageSize, sortBy, sortDir);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<BlogPostDto> getBlogPostById(@PathVariable Long id) {
		return ResponseEntity.ok(blogPostService.getBlogPostById(id));
	}
	
	@PutMapping("/put/{id}")
    public ResponseEntity<BlogPostDto> updatePost(@Valid @RequestBody BlogPostDto blogPostDto, @PathVariable(name = "id") long id){

       BlogPostDto postResponse = blogPostService.updateBlogPost(blogPostDto, id);

       return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        blogPostService.deleteBlogPostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}
