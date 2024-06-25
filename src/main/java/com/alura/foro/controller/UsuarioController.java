package com.alura.foro.controller;

import com.alura.foro.dominio.topico.DatosCreacionTopico;
import com.alura.foro.dominio.topico.DatosMostrarTopico;
import com.alura.foro.dominio.topico.Topico;
import com.alura.foro.dominio.usuario.DatosMostrarUsuario;
import com.alura.foro.dominio.usuario.DatosUsuario;
import com.alura.foro.dominio.usuario.Usuario;
import com.alura.foro.dominio.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<DatosMostrarUsuario>> listadoUsuarios(@PageableDefault(size = 5) Pageable paginacion)
    {
        var todosLosUsuarios = usuarioRepository.findByActivoTrue(paginacion);

        return ResponseEntity.ok(todosLosUsuarios.map(DatosMostrarUsuario::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarUsuario> unUsuario(@PathVariable Long id)
    {
        var usuarioParaMostrar = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosMostrarUsuario(usuarioParaMostrar));
    }
}
