package com.example.demo.controllers;

import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.model.request.AddCommentRequest;
import com.example.demo.model.request.AddPostRequest;
import com.example.demo.model.request.UpdateCommentRequest;
import com.example.demo.model.request.UpdatePostRequest;
import com.example.demo.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public Post createPost(@RequestBody AddPostRequest postRequest) {
        return postService.createPost(postRequest);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest postRequest) {
        return postService.updatePost(id, postRequest);
    }

    @DeleteMapping("/{id}")
    public boolean deletePostById(@PathVariable Long id) {
        return postService.deletePostById(id);
    }

    @PostMapping("/{id}/comments")
    public Comment addCommentToPost(@PathVariable Long id, @RequestBody AddCommentRequest commentRequest) {
        return postService.addCommentToPost(id, commentRequest);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getAllCommentsForPost(@PathVariable Long id) {
        return postService.getAllCommentsForPost(id);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody
    UpdateCommentRequest comment) {
        return postService.updateComment(postId, commentId, comment);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public boolean deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        return postService.deleteComment(postId, commentId);
    }
}
