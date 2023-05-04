package com.sprta.hanghae992.service;

import com.sprta.hanghae992.dto.MsgResponseDto;
import com.sprta.hanghae992.dto.PostRequestDto;
import com.sprta.hanghae992.dto.PostResponseDto;
import com.sprta.hanghae992.entity.Post;
import com.sprta.hanghae992.entity.User;
import com.sprta.hanghae992.entity.UserRoleEnum;
import com.sprta.hanghae992.jwt.JwtUtil;
import com.sprta.hanghae992.repository.CommentRepository;
import com.sprta.hanghae992.repository.PostRepository;
import com.sprta.hanghae992.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        // Request에서 Token 가져오기
            Post post = postRepository.saveAndFlush(new Post(postRequestDto, user.getId(),user.getUsername()));
            return new PostResponseDto(post);
        }




    //게시글 전체 조회
    @Transactional
    public List<PostResponseDto> getPost() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList();
        for(Post post : postList){
            postResponseDtoList.add(new PostResponseDto(post));
        }


//        List<Post> posts = postRepository.findAllFetchJoin();
//        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
        return postResponseDtoList;
    }

    //게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto,  User user) {

        //사용자 권환 확인 (ADMUN인지 아닌지)
        UserRoleEnum userRoleEnum = user.getRole();
        System.out.println("role = " + userRoleEnum);
        if (userRoleEnum == UserRoleEnum.ADMIN) {
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
            );
            post.update(postRequestDto);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return postResponseDto;
        } else {

            Post post= postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
            );

                post.update(postRequestDto);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return postResponseDto;
            }
        }




    //게시글 삭제
    @Transactional
    public MsgResponseDto deleteMemo(Long id, User user) {

            //사용자 권환 확인 (ADMUN인지 아닌지)
            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);
            if (userRoleEnum == UserRoleEnum.ADMIN) {
                Post post = postRepository.findById(id).orElseThrow(
                        () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
                );
                postRepository.deleteById(id);
                return new MsgResponseDto("게시글 삭제 성공", 200);
            } else {
                Post post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
                );
            }

            MsgResponseDto msgResponseDto = new MsgResponseDto("게시글 삭제 실패", 400);
            return msgResponseDto;

        }





    //게시글 상세조회
    public PostResponseDto oneGetPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }
}
