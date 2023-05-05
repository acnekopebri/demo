package com.example.demo.model.request;

import lombok.Data;

@Data
public class AddPostRequest {
    private String title;
    private String content;
    private Long authorId;
}
