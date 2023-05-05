package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Author;
import com.example.demo.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> expectedAuthors = new ArrayList<>();
        expectedAuthors.add(new Author(1L, "John Doe", "john.doe@example.com"));
        expectedAuthors.add(new Author(2L, "Jane Doe", "jane.doe@example.com"));

        when(authorRepository.findAll()).thenReturn(expectedAuthors);

        List<Author> actualAuthors = authorService.getAllAuthors();

        assertEquals(expectedAuthors.size(), actualAuthors.size());
        for (int i = 0; i < expectedAuthors.size(); i++) {
            assertEquals(expectedAuthors.get(i).getId(), actualAuthors.get(i).getId());
            assertEquals(expectedAuthors.get(i).getName(), actualAuthors.get(i).getName());
            assertEquals(expectedAuthors.get(i).getEmail(), actualAuthors.get(i).getEmail());
        }

        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuthorById() {
        Long id = 1L;
        Author expectedAuthor = new Author(id, "John Doe", "john.doe@example.com");

        when(authorRepository.findById(id)).thenReturn(Optional.of(expectedAuthor));

        Author actualAuthor = authorService.getAuthorById(id);

        assertNotNull(actualAuthor);
        assertEquals(expectedAuthor.getId(), actualAuthor.getId());
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
        assertEquals(expectedAuthor.getEmail(), actualAuthor.getEmail());

        verify(authorRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        Long id = 1L;

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Author actualAuthor = authorService.getAuthorById(id);

        assertNull(actualAuthor);

        verify(authorRepository, times(1)).findById(id);
    }

    @Test
    public void testCreateAuthor() {
        Author expectedAuthor = new Author(null, "John Doe", "john.doe@example.com");

        when(authorRepository.save(expectedAuthor)).thenReturn(new Author(1L, "John Doe", "john.doe@example.com"));

        Author actualAuthor = authorService.createAuthor(expectedAuthor);

        assertNotNull(actualAuthor);
        assertNotNull(actualAuthor.getId());
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
        assertEquals(expectedAuthor.getEmail(), actualAuthor.getEmail());

        verify(authorRepository, times(1)).save(expectedAuthor);
    }

    @Test
    public void testUpdateAuthor() {
        Author expectedAuthor = new Author(1L, "John Doe", "john.doe@example.com");

        when(authorRepository.save(expectedAuthor)).thenReturn(expectedAuthor);

        Author actualAuthor = authorService.updateAuthor(expectedAuthor);

        assertNotNull(actualAuthor);
        assertEquals(expectedAuthor.getId(), actualAuthor.getId());
        assertEquals(expectedAuthor.getName(), actualAuthor.getName());
        assertEquals(expectedAuthor.getEmail(), actualAuthor.getEmail());

        verify(authorRepository, times(1)).save(expectedAuthor);
    }

    @Test
    public void testDeleteAuthor() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(authorRepository).deleteById(1L);

        Boolean result = authorService.deleteAuthorById(1L);

        assertTrue(result);

        verify(authorRepository, times(1)).deleteById(1L);
        verify(authorRepository, times(1)).existsById(1L);
    }
}