package com.example.demo.controllers;

import com.example.demo.entities.Author;
import com.example.demo.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
    private static final String API_AUTHORS = "/api/authors";

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void testGetAllAuthors() throws Exception {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("John Doe");
        author1.setEmail("john.doe@example.com");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Jane Doe");
        author2.setEmail("jane.doe@example.com");

        List<Author> authors = Arrays.asList(author1, author2);

        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get(API_AUTHORS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")))
                .andExpect(jsonPath("$[1].email", is("jane.doe@example.com")));

        verify(authorService).getAllAuthors();
    }

    @Test
    void testGetAuthorById() throws Exception {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setName("John Doe");
        author.setEmail("john.doe@example.com");

        when(authorService.getAuthorById(authorId)).thenReturn(author);

        mockMvc.perform(get(API_AUTHORS + "/" + authorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));

        verify(authorService).getAuthorById(authorId);
    }

    @Test
    void testGetAuthorByIdNotFound() throws Exception {
        Long authorId = 1L;

        when(authorService.getAuthorById(authorId)).thenReturn(null);

        mockMvc.perform(get(API_AUTHORS + "/" + authorId))
                .andExpect(status().isNotFound());

        verify(authorService).getAuthorById(authorId);
    }

    @Test
    void testCreateAuthor() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        author.setEmail("john.doe@example.com");

        when(authorService.createAuthor(org.mockito.ArgumentMatchers.any(Author.class))).thenReturn(author);

        mockMvc.perform(post(API_AUTHORS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));

        verify(authorService).createAuthor(org.mockito.ArgumentMatchers.any(Author.class));
    }

    @Test
    public void testUpdateAuthor() {
        Long authorId = 1L;
        Author authorToUpdate = new Author();
        authorToUpdate.setName("John Doe");
        authorToUpdate.setEmail("johndoe@example.com");
        Author updatedAuthor = new Author();
        updatedAuthor.setId(authorId);
        updatedAuthor.setName("John Doe Jr.");
        updatedAuthor.setEmail("johndoeejr@example.com");
        when(authorService.updateAuthor(org.mockito.ArgumentMatchers.any(Author.class))).thenReturn(updatedAuthor);

        ResponseEntity<Author> responseEntity = authorController.updateAuthor(authorId, authorToUpdate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedAuthor, responseEntity.getBody());
    }

    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;
        when(authorService.deleteAuthorById(authorId)).thenReturn(true);
        ResponseEntity<Void> responseEntity = authorController.deleteAuthor(authorId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}