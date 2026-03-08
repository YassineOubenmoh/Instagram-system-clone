package com.yassine.insta_clone_backend.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yassine.insta_clone_backend.entities.Post;
import com.yassine.insta_clone_backend.entities.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String commentText;
    private LocalDateTime createdAt;
    private String commenterUsername;
    private Long postId;
}
