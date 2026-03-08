package com.yassine.insta_clone_backend.dtos;

import com.yassine.insta_clone_backend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerDto {
    private String userFollowingUsername;
    private String userFollowedUsername;
    private LocalDateTime createdAt;
}
