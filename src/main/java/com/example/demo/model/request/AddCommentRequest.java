package com.example.demo.model.request;

import lombok.Data;

@Data
public class AddCommentRequest {
    private String content;
    private Long authorId;
}
