package com.yassine.insta_clone_backend.repositories;

import com.yassine.insta_clone_backend.entities.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    @Query("SELECT f FROM Follower f WHERE f.userFollowing.id = :followerId")
    Set<Follower> findFollowerByFollowerId(@Param("followerId") Long followerId);


}
