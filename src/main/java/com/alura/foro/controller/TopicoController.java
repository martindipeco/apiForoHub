package com.alura.foro.controller;


import com.alura.foro.dominio.respuesta.DatosAgregarRespuesta;
import com.alura.foro.dominio.respuesta.Respuesta;
import com.alura.foro.dominio.topico.DatosCreacionTopico;
import com.alura.foro.dominio.topico.DatosMostrarTopico;
import com.alura.foro.dominio.topico.Topico;
import com.alura.foro.dominio.topico.TopicoRepository;
import com.alura.foro.dominio.usuario.Usuario;
import com.alura.foro.dominio.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @PutMapping
    public void agregarRespuesta(@RequestBody @Valid DatosAgregarRespuesta datosAgregarRespuesta)
    {
        Usuario usuario = null;
        var usuarioOpcional = usuarioRepository.findById(datosAgregarRespuesta.usuarioId());
        if (usuarioOpcional.isPresent())
        {
            usuario = usuarioOpcional.get();
        }
        else
        {
            throw new RuntimeException("No existe ese usuario");
        }

        Topico topicoParaEditar = null;
        var topicoOpcional = topicoRepository.findById(datosAgregarRespuesta.topicoId());
        if (topicoOpcional.isPresent())
        {
            topicoParaEditar = topicoOpcional.get();
        }
        else
        {
            throw new RuntimeException("No existe ese topico");
        }

        var respuestaNueva = new Respuesta(datosAgregarRespuesta, topicoParaEditar, usuario);
        topicoParaEditar.addRespuesta(respuestaNueva);
        topicoRepository.save(topicoParaEditar);
    }

    @PutMapping("/{id}")
    public void desactivarTopico(@PathVariable Long id)
    {
        Topico topicoParaDesactivar = null;
        var topicoOpcional = topicoRepository.findById(id);
        if (topicoOpcional.isPresent())
        {
            topicoParaDesactivar = topicoOpcional.get();
        }
        else
        {
            throw new RuntimeException("No existe ese topico");
        }
        topicoParaDesactivar.desactivarTopico();
        topicoRepository.save(topicoParaDesactivar);
    }

    //BORRADO FISICO
    @DeleteMapping("/{id}")
    @Transactional
    public void borrarTopico(@PathVariable Long id)
    {
        Topico topicoParaBorrar = null;
        var topicoOpcional = topicoRepository.findById(id);
        if (topicoOpcional.isPresent())
        {
            topicoParaBorrar = topicoOpcional.get();
        }
        else
        {
            throw new RuntimeException("No existe ese topico");
        }
        topicoRepository.delete(topicoParaBorrar);
    }
}
