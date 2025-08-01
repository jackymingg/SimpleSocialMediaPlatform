package com.socialmedia.platform.repository;

import com.socialmedia.platform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}



