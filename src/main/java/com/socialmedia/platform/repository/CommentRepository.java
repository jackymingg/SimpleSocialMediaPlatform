package com.socialmedia.platform.repository;

import com.socialmedia.platform.model.Comment;
import com.socialmedia.platform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    @Procedure(procedureName = "sp_add_comment")
    void addComment(Long userId, Long postId, String content);
}
