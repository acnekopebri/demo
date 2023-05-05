package com.example.demo.services;

import com.example.demo.entities.Author;
import com.example.demo.entities.Post;
import com.example.demo.model.request.AddPostRequest;
import com.example.demo.model.request.UpdatePostRequest;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private PostService postService;

    @Test
    public void testGetAllPosts() {
        List<Post> posts = new ArrayList<>();
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Post 1");
        post1.setContent("Content of Post 1");
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Author 1");
        author1.setEmail("author1@example.com");
        post1.setAuthor(author1);
        posts.add(post1);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Post 2");
        post2.setContent("Content of Post 2");
        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Author 2");
        author2.setEmail("author2@example.com");
        post2.setAuthor(author2);
        posts.add(post2);

        when(postRepository.findAll()).thenReturn(posts);

        List<Post> result = postService.getAllPosts();

        assertEquals(2, result.size());
        assertEquals(post1.getTitle(), result.get(0).getTitle());
        assertEquals(post2.getContent(), result.get(1).getContent());
    }

    @Test
    public void testGetPostById() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Post 1");
        post.setContent("Content of Post 1");
        Author author = new Author();
        author.setId(1L);
        author.setName("Author 1");
        author.setEmail("author1@example.com");
        post.setAuthor(author);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(1L);

        assertNotNull(result);
        assertEquals(post.getTitle(), result.getTitle());
        assertEquals(post.getAuthor().getEmail(), result.getAuthor().getEmail());
    }

    @Test
    public void testCreatePost() {
        AddPostRequest request = new AddPostRequest();
        request.setTitle("New Post");
        request.setContent("Content of New Post");
        request.setAuthorId(1L);

        Author author = new Author();
        author.setId(1L);
        author.setName("Author 1");
        author.setEmail("author1@example.com");

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);

        when(authorRepository.findById(request.getAuthorId())).thenReturn(Optional.of(author));
        when(postRepository.save(post)).thenReturn(post);

        Post result = postService.createPost(request);

        assertNotNull(result);
        assertEquals(request.getTitle(), result.getTitle());
        assertEquals(request.getContent(), result.getContent());
        assertEquals(request.getAuthorId(), result.getAuthor().getId());
    }

    @Test
    public void testUpdatePost() {
        UpdatePostRequest request = new UpdatePostRequest();
        request.setTitle("Updated Post");
        request.setContent("Content of Updated Post");
        request.setAuthorId(2L);

        Post existingPost = new Post();
        existingPost.setId(1L);
        existingPost.setTitle("Post 1");
        existingPost.setContent("Content of Post 1");
        Author author = new Author();
        author.setId(1L);
        author.setName("Author 1");
        author.setEmail("author1@example.com");
        existingPost.setAuthor(author);

        when(postRepository.findById(existingPost.getId())).thenReturn(Optional.of(existingPost));
    }
}
