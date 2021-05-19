package com.example.abstractionizer.login.jwt3.utils;

import com.example.abstractionizer.login.jwt3.db.rmdb.entities.User;
import com.example.abstractionizer.login.jwt3.models.vo.UserInfoVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@AllArgsConstructor
@Component
public class JwtUtil {

    public static final String secretKey = "fjdaifjopqemcfjdokqmcjfcqpcfmeiqpocfj";
    public static final String prefix = "Bearer ";
    public static final Integer validity = 1000 * 60 * 60 * 24;

    private final ObjectMapper objectMapper;

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

    public UserInfoVo getUserInfoFromToken(String token) throws JsonProcessingException {
        return decode(getAllFromToken(token).get("UserInfo", String.class));
    }

    public String generateToken(String username, UserInfoVo userInfoVo) {
        return Jwts.builder()
                .setSubject(username)
                .claim("UserInfo", encode(userInfoVo))
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

    private String encode(UserInfoVo s) {
        byte[] bytes = null;

        try{
            String json = objectMapper.writeValueAsString(s);
            bytes = json.getBytes(StandardCharsets.UTF_8);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(bytes);
    }

    public UserInfoVo decode(String s) throws JsonProcessingException {
        byte[] bytes = Base64.getDecoder().decode(s);
        String json = new String(bytes, StandardCharsets.UTF_8);
        return objectMapper.readValue(json, UserInfoVo.class);
    }
}
