package com.alura.foro.controller;


import com.alura.foro.dominio.topico.DatosCreacionTopico;
import com.alura.foro.dominio.topico.DatosMostrarTopico;
import com.alura.foro.dominio.topico.Topico;
import com.alura.foro.dominio.topico.TopicoRepository;
import com.alura.foro.dominio.usuario.Usuario;
import com.alura.foro.dominio.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public void crearTopico(@RequestBody @Valid DatosCreacionTopico datosCreacionTopico)
    {
        Usuario usuario = null;
        Optional<Usuario> usuarioOpcional = usuarioRepository.findById(datosCreacionTopico.usuarioId());
        if (usuarioOpcional.isPresent())
        {
            usuario = usuarioOpcional.get();
        }
        else {
            throw new RuntimeException("No se encontró el usuario");
        }
        Topico topico = new Topico(datosCreacionTopico, usuario);
        topicoRepository.save(topico);
    }

    @GetMapping
    public List<DatosMostrarTopico> listadoTopicos()
    {
        var todosLosTopicos = topicoRepository.findAll();

        //var todosLosDatos = todosLosTopicos.stream().map(t -> new DatosMostrarTopico(t)).collect(Collectors.toList());
        //return todosLosTopicos;

        return todosLosTopicos.stream().map(DatosMostrarTopico::new).collect(Collectors.toList());
    }
}
