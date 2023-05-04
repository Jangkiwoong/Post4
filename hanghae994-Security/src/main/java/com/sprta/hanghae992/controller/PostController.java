package com.sprta.hanghae992.controller;

import com.sprta.hanghae992.dto.MsgResponseDto;
import com.sprta.hanghae992.dto.PostRequestDto;
import com.sprta.hanghae992.dto.PostResponseDto;
import com.sprta.hanghae992.security.UserDetailsImpl;
import com.sprta.hanghae992.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController{

    private final PostService postService;

    //게시글 작성
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(postRequestDto,  userDetails.getUser());
    }

    //게시글 목록조회
    @GetMapping("/posts")
    public List<PostResponseDto> getPost(){
        return postService.getPost();
    }

    //게시글 상세 조회
    @GetMapping("/post/{id}")
    public PostResponseDto onegetPost(@PathVariable Long id){
        return postService.oneGetPost(id);
    }

    //게시글 수정
    @PutMapping("/post/{id}")
    public  PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, postRequestDto, userDetails.getUser());
    }

    //게시글 삭제
    @DeleteMapping("/post/{id}")
    public MsgResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deleteMemo(id, userDetails.getUser());
    }
}
