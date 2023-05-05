package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "post_id")
    private Long postId;

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", content='" + content + '\'' + ", authorId=" + authorId + ", postId="
                + postId + '}';
    }
}

