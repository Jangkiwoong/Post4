package com.sprta.hanghae992.controller;


import com.sprta.hanghae992.dto.CmtRequestDto;
import com.sprta.hanghae992.security.UserDetailsImpl;
import com.sprta.hanghae992.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/comment")
    public ResponseEntity addComment(@RequestBody CmtRequestDto cmtRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.addComment(cmtRequestDto, userDetails.getUser());
    }

    //댓글 수정
    @PutMapping("/comment/{id}")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody CmtRequestDto cmtRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, cmtRequestDto, userDetails.getUser());
    }


    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getUser());
    }
}
