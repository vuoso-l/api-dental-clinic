package com.example.dentalClinicApi.security;

import com.example.dentalClinicApi.exception.ClinicAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

//se genera token y se validan, obtiene claims
@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        //se obtiene el usuario del token
        String userName = authentication.getName();

        //fecha actual - fecha de expiracion
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        //se establecen los datos al token
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJWT(String token) {
        //Claims -> son los datos del token
        //getSubject() obtiene el usuario del token
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //se autentica el token
    public boolean authToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }catch (MalformedJwtException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch (ExpiredJwtException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }catch(UnsupportedJwtException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }catch (IllegalArgumentException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}
