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

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveDto {
    private LocalDateTime createdAt;
    private String userSavingUsername;
    private Long postSavedId;
}
