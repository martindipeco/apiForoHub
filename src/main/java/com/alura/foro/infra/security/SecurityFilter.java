package com.alura.foro.infra.security;

import com.alura.foro.dominio.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("El filtro está siendo llamado");
        //obtener token del header
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null)
        {
            var token = authHeader.replace("Bearer ", "");
            System.out.println(authHeader);
            System.out.println(tokenService.getSubject(token));
            var subject = tokenService.getSubject(token);
            if (subject != null)
            {
                //si llega el subject, el token es válido
                var usuario = usuarioRepository.findByNombre(subject);
                var authentication = new UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.getAuthorities()); //forzar inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
