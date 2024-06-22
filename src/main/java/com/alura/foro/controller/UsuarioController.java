package com.alura.foro.controller;

import com.alura.foro.dominio.topico.DatosCreacionTopico;
import com.alura.foro.dominio.topico.Topico;
import com.alura.foro.dominio.usuario.DatosUsuario;
import com.alura.foro.dominio.usuario.Usuario;
import com.alura.foro.dominio.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public void crearUsuario(@RequestBody @Valid DatosUsuario datosUsuario)
    {
        var usuario = new Usuario(datosUsuario);
        usuarioRepository.save(usuario);
    }
}
