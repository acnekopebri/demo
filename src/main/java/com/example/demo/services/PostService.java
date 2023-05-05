package com.example.demo.services;

import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository    postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post post) {
        Post existingPost = postRepository.findById(id).orElse(null);
        if (existingPost != null) {
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            existingPost.setAuthor(post.getAuthor());
            existingPost.setComments(post.getComments());
            return postRepository.save(existingPost);
        }
        return null;
    }

    public boolean deletePostById(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Comment addCommentToPost(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            comment.setPostId(post.getId());
            return commentRepository.save(comment);
        }
        return null;
    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            return commentRepository.findByPostId(postId);
        }
        return null;
    }

    public Comment updateComment(Long postId, Long commentId, Comment updatedComment) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null && comment.getPostId().equals(postId)) {
            comment.setContent(updatedComment.getContent());
            return commentRepository.save(comment);
        }
        return null;
    }

    public boolean deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null && comment.getPostId().equals(postId)) {
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }
}
