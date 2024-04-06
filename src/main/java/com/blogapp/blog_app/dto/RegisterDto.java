package com.blogapp.blog_app.dto;


import com.blogapp.blog_app.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

	private String name;
    private String username;
    private String email;
    private String password;
    private Role role;
}
