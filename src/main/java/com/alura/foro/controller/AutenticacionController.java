package com.alura.foro.controller;

import com.alura.foro.dominio.usuario.DatosLoginUsuario;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosLoginUsuario datosLoginUsuario)
    {
        var tokenAutenticacion = new UsernamePasswordAuthenticationToken(
                datosLoginUsuario.nombre(), datosLoginUsuario.password());
        authenticationManager.authenticate(tokenAutenticacion);
        return ResponseEntity.ok().build();
    }
}
