package com.example.demo.services;

import com.example.demo.entities.Author;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.model.request.AddCommentRequest;
import com.example.demo.model.request.AddPostRequest;
import com.example.demo.model.request.UpdateCommentRequest;
import com.example.demo.model.request.UpdatePostRequest;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository    postRepository;
    private final CommentRepository commentRepository;
    private final AuthorRepository  authorRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository,
            AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.authorRepository = authorRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(AddPostRequest postRequest) {
        Author author = authorRepository.findById(postRequest.getAuthorId())
                .orElseThrow();

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(author);

        System.out.println(post);

        return postRepository.save(post);
    }

    public Post updatePost(Long id, UpdatePostRequest postRequest) {
        Post existingPost = postRepository.findById(id).orElse(null);
        if (existingPost != null) {
            Author author = authorRepository.findById(postRequest.getAuthorId())
                    .orElseThrow();

            existingPost.setTitle(postRequest.getTitle());
            existingPost.setContent(postRequest.getContent());
            existingPost.setAuthor(author);
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

    public Comment addCommentToPost(Long postId, AddCommentRequest commentRequest) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            Comment comment = new Comment();
            comment.setPostId(post.getId());
            comment.setContent(commentRequest.getContent());
            comment.setAuthorId(commentRequest.getAuthorId());
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

    public Comment updateComment(Long postId, Long commentId, UpdateCommentRequest updatedComment) {
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
