package com.yassine.insta_clone_backend.dtos;


import com.yassine.insta_clone_backend.entities.Comment;
import com.yassine.insta_clone_backend.entities.User;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String postImageName;
    private byte[] postImage;
    private String caption;
    private String userPostingUsername;
    private String timePassedBetweenNowAndDatePublication;
}
