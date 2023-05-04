package com.sprta.hanghae992.repository;

import com.sprta.hanghae992.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {
      List<Post> findAllByOrderByModifiedAtDesc();  //전체조회
      Optional<Post> findByIdAndUserId(Long id, Long userId);    //수정



}
