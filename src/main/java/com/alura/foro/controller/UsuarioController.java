package com.alura.foro.controller;

import com.alura.foro.dominio.usuario.DatosMostrarUsuario;
import com.alura.foro.dominio.usuario.DatosCreacionUsuario;
import com.alura.foro.dominio.usuario.Usuario;
import com.alura.foro.dominio.usuario.UsuarioRepository;
import com.alura.foro.infra.exception.ValidacionIntegridadException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public void crearUsuario(@RequestBody @Valid DatosCreacionUsuario datosUsuario)
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
        //Validación de integridad
        if (usuarioRepository.findById(id).isEmpty())
        {
            throw new ValidacionIntegridadException("No se encontró usuario con ese ID");
        }

        var usuarioParaMostrar = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosMostrarUsuario(usuarioParaMostrar));
    }
}
