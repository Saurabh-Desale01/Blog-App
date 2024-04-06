package com.blogapp.blog_app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blogapp.blog_app.dto.CommentDto;
import com.blogapp.blog_app.entity.BlogPost;
import com.blogapp.blog_app.entity.Comment;
import com.blogapp.blog_app.exception.BlogAPIException;
import com.blogapp.blog_app.exception.ResourceNotFoundException;
import com.blogapp.blog_app.repository.BlogPostRepository;
import com.blogapp.blog_app.repository.CommentRepository;
import com.blogapp.blog_app.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		Comment comment = mapToEntity(commentDto);
		
		 BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(
	                () -> new ResourceNotFoundException("BlogPost", "id", postId));
		
		comment.setBlogPost(blogPost);
		
		Comment newComment = commentRepository.save(comment);
		
		return mapToDto(newComment);
	}
	
	@Override
	public List<CommentDto> getCommentByPostId(long postId) {
		
		List<Comment> allComments = commentRepository.findByBlogPostId(postId);
		
		return allComments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}
	
	@Override
	public CommentDto getCommentById(long postId, long id) {
	
		BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("BlogPost", "id", postId));
		
		Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id));
		
		if(!comment.getBlogPost().getId().equals(blogPost.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
		
		return mapToDto(comment);
	}
	
	@Override
	public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
		

		BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("BlogPost", "id", postId));
		
		Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id));
		
		if(!comment.getBlogPost().getId().equals(blogPost.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
		
		comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);

	}
	
	@Override
	public void deleteComment(long postId, long id) {
		
		BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("BlogPost", "id", postId));
		
		Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id));
		
		if(!comment.getBlogPost().getId().equals(blogPost.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
		
		commentRepository.delete(comment);
		
	}
	
	private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return  commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
        
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return  comment;
    }

}
