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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<DatosMostrarTopico> crearTopico(@RequestBody @Valid DatosCreacionTopico datosCreacionTopico,
                                      UriComponentsBuilder uriComponentsBuilder)
    {
//        Usuario usuario = null;
//        Optional<Usuario> usuarioOpcional = usuarioRepository.findById(datosCreacionTopico.usuarioId());
//        if (usuarioOpcional.isPresent())
//        {
//            usuario = usuarioOpcional.get();
//        }
//        else {
//            throw new RuntimeException("No se encontró el usuario");
//        }
        var usuario = usuarioRepository.getReferenceById(datosCreacionTopico.usuarioId());
        Topico topico = new Topico(datosCreacionTopico, usuario);
        topicoRepository.save(topico);
        var datosMostrarTopico = new DatosMostrarTopico(topico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosMostrarTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosMostrarTopico>> listadoTopicos(@PageableDefault(size = 5)Pageable paginacion)
    {
        //var todosLosTopicos = topicoRepository.findAll(paginacion);
        var todosLosTopicos = topicoRepository.findByActivoTrue(paginacion);
        //var todosLosDatos = todosLosTopicos.stream().map(t -> new DatosMostrarTopico(t)).collect(Collectors.toList());
        //return todosLosTopicos;

        return ResponseEntity.ok(todosLosTopicos.map(DatosMostrarTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarTopico> unTopico(@PathVariable Long id)
    {
//        Topico topicoParaMostrar = null;
//        var topicoOpcional = topicoRepository.findById(id);
//        if (topicoOpcional.isPresent())
//        {
//            topicoParaMostrar = topicoOpcional.get();
//        }
//        else
//        {
//            throw new RuntimeException("No existe ese topico");
//        }
        var topicoParaMostrar = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosMostrarTopico(topicoParaMostrar));
    }

    @PutMapping
    //@Transactional no hace falta x q estamos cerrando con save del repositorio
    public ResponseEntity agregarRespuesta(@RequestBody @Valid DatosAgregarRespuesta datosAgregarRespuesta)
    {
//        Usuario usuario = null;
//        var usuarioOpcional = usuarioRepository.findById(datosAgregarRespuesta.usuarioId());
//        if (usuarioOpcional.isPresent())
//        {
//            usuario = usuarioOpcional.get();
//        }
//        else
//        {
//            throw new RuntimeException("No existe ese usuario");
//        }
        var usuario = usuarioRepository.getReferenceById(datosAgregarRespuesta.usuarioId());

//        Topico topicoParaEditar = null;
//        var topicoOpcional = topicoRepository.findById(datosAgregarRespuesta.topicoId());
//        if (topicoOpcional.isPresent())
//        {
//            topicoParaEditar = topicoOpcional.get();
//        }
//        else
//        {
//            throw new RuntimeException("No existe ese topico");
//        }
        var topicoParaEditar = topicoRepository.getReferenceById(datosAgregarRespuesta.topicoId());

        var respuestaNueva = new Respuesta(datosAgregarRespuesta, topicoParaEditar, usuario);
        topicoParaEditar.addRespuesta(respuestaNueva);
        topicoRepository.save(topicoParaEditar);
        return ResponseEntity.ok(new DatosMostrarTopico(topicoParaEditar));
    }

    //BORRADO LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desactivarTopico(@PathVariable Long id)
    {
//        Topico topicoParaDesactivar = null;
//        var topicoOpcional = topicoRepository.findById(id);
//        if (topicoOpcional.isPresent())
//        {
//            topicoParaDesactivar = topicoOpcional.get();
//        }
//        else
//        {
//            throw new RuntimeException("No existe ese topico");
//        }
        var topicoParaDesactivar = topicoRepository.getReferenceById(id);

        topicoParaDesactivar.desactivarTopico();
        //topicoRepository.save(topicoParaDesactivar); //no hace falta x q estoy usando transactional
        return ResponseEntity.noContent().build();
    }

    //BORRADO FISICO
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity borrarTopico(@PathVariable Long id)
    {
//        Topico topicoParaBorrar = null;
//        var topicoOpcional = topicoRepository.findById(id);
//        if (topicoOpcional.isPresent())
//        {
//            topicoParaBorrar = topicoOpcional.get();
//        }
//        else
//        {
//            throw new RuntimeException("No existe ese topico");
//        }

        var topicoParaBorrar = topicoRepository.getReferenceById(id);

        topicoRepository.delete(topicoParaBorrar);
        return ResponseEntity.noContent().build();
    }
}
