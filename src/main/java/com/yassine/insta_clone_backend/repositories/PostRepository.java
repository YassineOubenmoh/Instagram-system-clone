package com.yassine.insta_clone_backend.repositories;

import com.yassine.insta_clone_backend.entities.Post;
import com.yassine.insta_clone_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p FROM Post p WHERE p.userPosting.username = :username")
    Set<Post> findPostsByUser(@Param("username") String username);
}
