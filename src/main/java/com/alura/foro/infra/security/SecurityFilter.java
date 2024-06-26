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
        System.out.println("doFilterInternal desde SecurityFilter.java está siendo llamado");
        //obtener token del header
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null)
        {
            System.out.println(authHeader);
            var token = authHeader.replace("Bearer ", "");
            System.out.println(token);
            var subject = tokenService.getSubject(token);
            System.out.println(subject);
            if (subject != null)
            {
                //si llega el subject, el token es válido
                var usuario = usuarioRepository.findByNombre(subject);
                var authentication = new UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.getAuthorities()); //forzar inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        System.out.println("Inicio filterChain.doFilter");
        filterChain.doFilter(request, response);
        System.out.println("Fin filterChain.doFilter");
    }
}
