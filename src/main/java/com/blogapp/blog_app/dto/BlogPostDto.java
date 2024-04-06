package com.blogapp.blog_app.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BlogPostDto {
	
	private long id;
	
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;
	
	@NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
	private String description;
	
	@NotEmpty
	private String context;
	private Set<CommentDto> comments;

}
