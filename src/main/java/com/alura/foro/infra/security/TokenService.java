package com.alura.foro.infra.security;

import com.alura.foro.dominio.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.infra.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario)
    {
        System.out.println("generarToken desde TokenService.java");
        String token = "";
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); //secret p validar firma
            token = JWT.create()
                    .withIssuer("foro")
                    .withSubject(usuario.getNombre())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarExpiracionToken(22))
                    .sign(algorithm);
        }
        catch (JWTCreationException e)
        {
            throw new RuntimeException("Ocurrió un problema en método generarToken");
        }
        return token;
    }

    private Instant generarExpiracionToken(Integer duracionHoras)
    {
        return LocalDateTime.now().plusHours(duracionHoras).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token)
    {
        System.out.println("getSubject desde TokenService.java");
        if (token == null)
        {
            throw new RuntimeException("Token llegó null");
        }
        DecodedJWT decodedJWT = null;
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            decodedJWT = JWT.require(algorithm)
                    .withIssuer("foro")
                    .build()
                    .verify(token);
            decodedJWT.getSubject();
        }
        catch(JWTVerificationException e)
        {
            System.out.println("Excepción JWTVerification. " + e);
        }
        if (decodedJWT.getSubject() == null)
        {
            throw new RuntimeException("Verificador JWT es nulll");
        }
        return decodedJWT.getSubject();
    }
}
