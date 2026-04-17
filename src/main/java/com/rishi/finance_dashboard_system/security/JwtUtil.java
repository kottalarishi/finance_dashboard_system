package com.rishi.finance_dashboard_system.security;

import com.rishi.finance_dashboard_system.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.access-expiration}")
    private long accessTokenExpiration;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    private Key key;

    @PostConstruct
    public  void initialize(){
      byte[] bytes= Decoders.BASE64.decode(jwtSecret);
         this.key = Keys.hmacShaKeyFor(bytes);
    }


    public String generateAccessToken(User user){
        Date present= new Date();
        Date expiry= new Date(present.getTime()+accessTokenExpiration);

        return Jwts.builder()
                .setSubject(user.getEmail()).
                claim("userId",user.getId())
                .claim("roles",user.getRole().name())
                .setIssuedAt(present).setExpiration(expiry)
                .signWith(key).compact();

    }

//    public String generateRefreshToken(User user){
//
//        Date present= new Date();
//        Date expiry= new Date(present.getTime()+refreshTokenExpiration);
//
//        return Jwts.builder()
//                .setSubject(user.getEmail()).
//                setIssuedAt(present).
//                setExpiration(expiry).
//                signWith(key).compact();
//    }

    public  boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key)
                    .build().parseClaimsJws(token);
            return true;
        }catch (JwtException |IllegalArgumentException exception){
            return false;
        }
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().
                setSigningKey(key).build().parseClaimsJws(token).getBody();

    }

    public String getEmailFromToken(String token){
        return extractAllClaims(token).getSubject();
    }
    public  String getRoleFromToken(String token){
        return  extractAllClaims(token).get("roles",String.class);
    }














}
