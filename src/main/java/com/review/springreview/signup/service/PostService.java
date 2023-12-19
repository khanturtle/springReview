package com.review.springreview.signup.service;

import com.review.springreview.signup.dto.PostRequestDto;
import com.review.springreview.signup.dto.PostResponseDto;
import com.review.springreview.signup.entity.Post;
import com.review.springreview.signup.repository.PostRepository;
import com.review.springreview.signup.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    //게시글 생성
    public PostResponseDto createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = new Post(postRequestDto,userDetails);
        postRepository.save(post);
        return new PostResponseDto(post);
    }
    //게시글 전체 조회
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        List<Post> postList = postRepository.findAll();

        for(Post post:postList){
            postResponseDtoList.add(new PostResponseDto(post));
        }
        postResponseDtoList.sort(new TimeComparator().reversed());
        return postResponseDtoList;
    }

    public static class TimeComparator implements Comparator<PostResponseDto> {
        @Override
        public int compare(PostResponseDto post1,PostResponseDto post2) {
            return post1.getCreatedAt().compareTo(post2.getCreatedAt());
        }
    }
}
