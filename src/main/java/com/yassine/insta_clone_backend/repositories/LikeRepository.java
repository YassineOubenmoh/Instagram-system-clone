package com.yassine.insta_clone_backend.repositories;

import com.yassine.insta_clone_backend.entities.Like;
import com.yassine.insta_clone_backend.entities.Post;
import com.yassine.insta_clone_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l.userLiking FROM Like l WHERE l.post.id = :postId")
    Set<User> findAccountsLikingPost(@Param("postId") Long postId);
}
