package com.yassine.insta_clone_backend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String bio;
    private String imageName;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonManagedReference("user_post")
    @OneToMany(mappedBy = "userPosting", cascade = CascadeType.ALL)
    private Set<Post> posts;

    @JsonManagedReference("user_comment")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @JsonManagedReference("user_like")
    @OneToMany(mappedBy = "userLiking", cascade = CascadeType.ALL)
    private Set<Like> likes;

    @JsonManagedReference("user_follower")
    @OneToMany(mappedBy = "userFollowing", cascade = CascadeType.ALL)
    private Set<Follower> followersList;

    @JsonManagedReference("user_followed")
    @OneToMany(mappedBy = "userFollowed", cascade = CascadeType.ALL)
    private Set<Follower> followedList;

    @JsonManagedReference("user_save")
    @OneToMany(mappedBy = "userSaving", cascade = CascadeType.ALL)
    private Set<Save> saveList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "'}";
    }
}
