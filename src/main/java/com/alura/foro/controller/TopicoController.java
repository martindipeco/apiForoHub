package com.alura.foro.controller;


import com.alura.foro.dominio.respuesta.DatosAgregarRespuesta;
import com.alura.foro.dominio.respuesta.Respuesta;
import com.alura.foro.dominio.topico.*;
import com.alura.foro.dominio.usuario.Usuario;
import com.alura.foro.dominio.usuario.UsuarioRepository;
import com.alura.foro.infra.exception.ValidacionIntegridadException;
import com.alura.foro.infra.validacion.ValidadorRespuesta;
import com.alura.foro.infra.validacion.ValidadorTopico;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<DatosMostrarTopico> crearTopico(@RequestBody @Valid DatosCreacionTopico datosCreacionTopico,
                                      UriComponentsBuilder uriComponentsBuilder)
    {
        var datosMostrarTopico = topicoService.crearTopico(datosCreacionTopico);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datosMostrarTopico.id()).toUri();
        return ResponseEntity.created(url).body(datosMostrarTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosMostrarTopico>> listadoTopicos(@PageableDefault(size = 5)Pageable paginacion)
    {
        var todosLosTopicos = topicoRepository.findByActivoTrue(paginacion);

        return ResponseEntity.ok(todosLosTopicos.map(DatosMostrarTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosMostrarTopico> unTopico(@PathVariable Long id)
    {

        //Validación de integridad
        if (topicoRepository.findById(id).isEmpty())
        {
            throw new ValidacionIntegridadException("No se encontró tópico con ese ID");
        }

        var topicoParaMostrar = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosMostrarTopico(topicoParaMostrar));
    }

    @PutMapping
    //@Transactional no hace falta x q estamos cerrando con save del repositorio
    public ResponseEntity agregarRespuesta(@RequestBody @Valid DatosAgregarRespuesta datosAgregarRespuesta)
    {
        var topicoEditado = topicoService.agregarRespuesta(datosAgregarRespuesta);

        return ResponseEntity.ok(topicoEditado);
    }

    //BORRADO LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desactivarTopico(@PathVariable Long id)
    {
        //Validación de integridad
        if (topicoRepository.findById(id).isEmpty())
        {
            throw new ValidacionIntegridadException("No se encontró tópico con ese ID");
        }

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

        //Validación de integridad
        if (topicoRepository.findById(id).isEmpty())
        {
            throw new ValidacionIntegridadException("No se encontró tópico con ese ID");
        }

        var topicoParaBorrar = topicoRepository.getReferenceById(id);

        topicoRepository.delete(topicoParaBorrar);
        return ResponseEntity.noContent().build();
    }
}
