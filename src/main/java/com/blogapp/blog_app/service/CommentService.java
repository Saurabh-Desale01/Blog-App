package com.blogapp.blog_app.service;

import java.util.List;

import com.blogapp.blog_app.dto.CommentDto;

public interface CommentService {

	public CommentDto createComment(long postId, CommentDto commentDto);
	
	public List<CommentDto> getCommentByPostId(long postId);
	
	public CommentDto getCommentById(long postId, long id);
	
	public CommentDto updateComment(long postId, long id, CommentDto commentDto);
	
	public void deleteComment(long postId, long id);
}
