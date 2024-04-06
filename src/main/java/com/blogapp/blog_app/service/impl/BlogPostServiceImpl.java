package com.blogapp.blog_app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blogapp.blog_app.dto.BlogPostDto;
import com.blogapp.blog_app.dto.BlogPostResponse;
import com.blogapp.blog_app.entity.BlogPost;
import com.blogapp.blog_app.exception.ResourceNotFoundException;
import com.blogapp.blog_app.repository.BlogPostRepository;
import com.blogapp.blog_app.service.BlogPostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService{

	private final BlogPostRepository blogPostRepository;
	
	private final ModelMapper mapper;
	
	
	@Override
	public BlogPostDto createPost(BlogPostDto blogPostDto) {
		
		//convert dto to entity
		BlogPost blogPost = mapToEntity(blogPostDto);
		BlogPost newBlogPost = blogPostRepository.save(blogPost);
		
		//convert entity to dto
		BlogPostDto responseBlogPost = mapToDto(newBlogPost);
		return responseBlogPost;
	}
	
	@Override
	public BlogPostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<BlogPost> blogPost = blogPostRepository.findAll(pageable);
		
		List<BlogPost> listOfPosts = blogPost.getContent();
		
		List<BlogPostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		BlogPostResponse blogPostResponse = new BlogPostResponse();
		blogPostResponse.setContent(content);
		blogPostResponse.setPageNo(blogPost.getNumber());
		blogPostResponse.setPageSize(blogPost.getSize());
		blogPostResponse.setTotalElements(blogPost.getTotalElements());
		blogPostResponse.setTotalPages(blogPost.getTotalPages());
		blogPostResponse.setLast(blogPost.isLast());

		return blogPostResponse;
	}
	
    @Override
    public BlogPostDto getBlogPostById(long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(blogPost);
    }
    
    @Override
    public BlogPostDto updateBlogPost(BlogPostDto blogPostDto, long id) {
        // get post by id from the database
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

//        Category category = categoryRepository.findById(postDto.getCategoryId())
//                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setDescription(blogPostDto.getDescription());
        blogPost.setContext(blogPostDto.getContext());
        //post.setCategory(category);
        BlogPost updatedPost = blogPostRepository.save(blogPost);
        return mapToDto(updatedPost);
    }
    
    @Override
    public void deleteBlogPostById(long id) {
        // get post by id from the database
        BlogPost post = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        blogPostRepository.delete(post);
      
    }
	
	private BlogPostDto mapToDto(BlogPost blogPost) {
		BlogPostDto blogPostDto = mapper.map(blogPost, BlogPostDto.class);
		
//		BlogPostDto blogPostDto = new BlogPostDto();
//		blogPostDto.setId(blogPost.getId());
//		blogPostDto.setTitle(blogPost.getTitle());
//		blogPostDto.setDescription(blogPost.getDescription());
//		blogPostDto.setContext(blogPost.getContext());
		return blogPostDto;
	}
	
	private BlogPost mapToEntity(BlogPostDto blogPostDto) {
		BlogPost blogPost = mapper.map(blogPostDto, BlogPost.class);
		
//		BlogPost blogPost = new BlogPost();
//		blogPost.setTitle(blogPostDto.getTitle());
//		blogPost.setDescription(blogPostDto.getDescription());
//		blogPost.setContext(blogPostDto.getContext());
		return blogPost;
	}



}
