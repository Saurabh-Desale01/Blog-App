package com.blogapp.blog_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {

	private String accessToken;
    //private String tokenType = "Bearer";
}
