package com.socialmedia.platform.repository;

import com.socialmedia.platform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserUserId(Long userId);

    @Procedure(procedureName = "sp_create_post")
    void createPost(Long userId, String content, String image);
}



