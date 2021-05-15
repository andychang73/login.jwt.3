package com.example.abstractionizer.login.jwt3.utils;

import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String secretKey = "fjdaifjopqemcfjdokqmcjfcqpcfmeiqpocfj";
    public static final String prefix = "Bearer ";
    public static final Integer validity = 1000 * 60 * 5;

    public boolean isTokenValid(String token, String username){
        return getClaimsFromToken(token, Claims::getSubject).equals(username) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return getClaimsFromToken(token, Claims::getExpiration).before(new Date());
    }

    public Date getExpiration(String token){
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public String getUsername(String token){
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public UserInfoVo getUserInfoFromToken(String token){
        return (UserInfoVo)getAllFromToken(token).get("UserInfo");
    }

    public String generateToken(String username, UserInfoVo userInfoVo){
        return Jwts.builder()
                .setSubject(username)
                .claim("UserInfo", userInfoVo)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Claims getAllFromToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> resolver){
        return resolver.apply(getAllFromToken(token));
    }
}
