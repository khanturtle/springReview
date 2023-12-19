package com.review.springreview.signup.repository;

import com.review.springreview.signup.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
