package com.review.springreview.signup.service;

import com.review.springreview.signup.dto.CommonResponseDto;
import com.review.springreview.signup.dto.PostRequestDto;
import com.review.springreview.signup.dto.PostResponseDto;
import com.review.springreview.signup.entity.Post;
import com.review.springreview.signup.repository.PostRepository;
import com.review.springreview.signup.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    //시간순 정렬
    public static class TimeComparator implements Comparator<PostResponseDto> {
        @Override
        public int compare(PostResponseDto post1,PostResponseDto post2) {
            return post1.getCreatedAt().compareTo(post2.getCreatedAt());
        }
    }
    //게시물 단건 조회
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );
        return new PostResponseDto(post);
    }
    //게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );
        if(post.getUser().getNickname().equals(userDetails.getUser().getNickname())){
            post.setTitle(postRequestDto.getTitle());
            post.setContent(postRequestDto.getContent());
            post.setModifiedAt(LocalDateTime.now());
        }else{
            throw new IllegalArgumentException("게시글 작성자가 아닙니다.");
        }
        return new PostResponseDto(post);
    }
    //게시글 삭제
    @Transactional
    public CommonResponseDto deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );
        if(post.getUser().getNickname().equals(userDetails.getUser().getNickname())){
            postRepository.delete(post);
            return new CommonResponseDto("게시글 삭제 완료", HttpStatus.OK.value());
        }else{
            return new CommonResponseDto("게시글 작성자가 아닙니다.",HttpStatus.BAD_REQUEST.value());
        }
    }
}
