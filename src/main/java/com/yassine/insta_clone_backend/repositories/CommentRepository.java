package com.yassine.insta_clone_backend.repositories;

import com.yassine.insta_clone_backend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT DISTINCT c FROM Comment c WHERE c.post.id = :postId")
    Set<Comment> findCommentsByPost(@Param("postId") Long postId);
}
