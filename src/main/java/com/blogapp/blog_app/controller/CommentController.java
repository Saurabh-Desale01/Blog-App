package com.blogapp.blog_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.blog_app.dto.CommentDto;
import com.blogapp.blog_app.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId, 
													@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{postId}/comment")
	public List<CommentDto> getCommentByPostId(@PathVariable long postId){
		return commentService.getCommentByPostId(postId);
	}
	
	@GetMapping("/get/{postId}/comment/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId,
									                 @PathVariable long id
			) {
		return new ResponseEntity<>(commentService.getCommentById(postId, id), HttpStatus.OK);
	}
	
	@PutMapping("/update/{postId}/comment/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable long postId,
													@PathVariable long id,
													@Valid @RequestBody CommentDto commentDto
			){
		return new ResponseEntity<>(commentService.updateComment(postId, id, commentDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{postId}/comment/{id}")
	public String deleteComment(@PathVariable long postId,
								@PathVariable long id) {
		commentService.deleteComment(postId, id);
		return "Comment Deleted Successfully";
	}
		
}
