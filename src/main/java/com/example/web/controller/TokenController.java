package com.example.web.controller;

import com.example.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequiredArgsConstructor
public class TokenController {
    private final JwtProvider jwtProvider;


    // 토큰 생성 컨트롤러
    @GetMapping(value = "/tokenCreate/{userId}")
    public TokenResponse<?> createToken(@PathVariable("userId") String userId) {
        String token = jwtProvider.createToken(userId); // 토큰 생성
        Claims claims = jwtProvider.parseJwtToken("Bearer "+ token); // 토큰 검증

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token, claims.getSubject(), claims.getIssuedAt().toString(), claims.getExpiration().toString());

        return new TokenResponse<>("200", "OK", tokenDataResponse);
    }

    //토큰 인증 컨트롤러
    @GetMapping(value = "/checkToken")
    public TokenResponseNoData<?> checkToken(@RequestHeader(value = "Authorization") String token) {
        Claims claims = jwtProvider.parseJwtToken(token);

        return new TokenResponseNoData<>("200", "success");
    }

    //Response DTO
    @Data
    @AllArgsConstructor
    static class TokenResponse<T> {

        private String code;
        private String msg;
        private T data;
    }

    //Response DTO
    @Data
    @AllArgsConstructor
    static class TokenResponseNoData<T> {

        private String code;
        private String msg;
    }

    //Response DTO
    @Data
    @AllArgsConstructor
    static class TokenDataResponse {
        private String token;
        private String subject;
        private String issued_time;
        private String expired_time;
    }
}
