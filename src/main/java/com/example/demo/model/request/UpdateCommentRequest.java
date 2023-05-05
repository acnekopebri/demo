package com.example.demo.model.request;

import lombok.Data;

@Data
public class UpdateCommentRequest {
    private String title;
    private String content;
    private Long authorId;
}
