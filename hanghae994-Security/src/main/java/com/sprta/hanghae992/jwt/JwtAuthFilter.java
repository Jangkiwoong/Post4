package com.sprta.hanghae992.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprta.hanghae992.dto.SecurityExceptionDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
//필터를 상속 받아서 filter를 만들고 filterchain을 통해 다음 filter로 이동
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

//        if(token == null){
//            jwtExceptionHandler(response, "Token null", HttpStatus.UNAUTHORIZED.value());
//            return;
//        }
        //게시글 작성과 같은 인증이 필요한 요청이 들어왔을때 여기서 대신한다.
        if(token != null) {
            if(!jwtUtil.validateToken(token)){
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            Claims info = jwtUtil.getUserInfoFromToken(token);
            setAuthentication(info.getSubject());
        }

        //회원가입과 로그인 url이 아닐때만 토큰이 없을때 에러 코드를 반환 @@@@@@@@@@@@@@@@@@@@
//        String uri = request.getRequestURI();
//        if(!uri.equals("/api/auth/login") && !uri.equals("/api/auth/signup") && !uri.equals("/api/posts") && token == null){
//            jwtExceptionHandler(response, "Token null", HttpStatus.UNAUTHORIZED.value());
//            return;
//        }

        filterChain.doFilter(request,response);
    }



    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    //핸들러에서 토큰의 대한 오류가 발생 했을 때 클라이언트에게 Exception처리 값을 알려 준다.
    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}