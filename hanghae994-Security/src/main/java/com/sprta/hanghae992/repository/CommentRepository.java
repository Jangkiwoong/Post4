package com.sprta.hanghae992.repository;

import com.sprta.hanghae992.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByIdAndUsername(Long id, String username);

}
