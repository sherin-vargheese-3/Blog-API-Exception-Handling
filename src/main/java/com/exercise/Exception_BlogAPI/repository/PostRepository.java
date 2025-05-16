package com.exercise.Exception_BlogAPI.repository;

import com.exercise.Exception_BlogAPI.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
