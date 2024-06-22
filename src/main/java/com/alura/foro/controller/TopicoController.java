package com.alura.foro.controller;


import com.alura.foro.dominio.topico.DatosCreacionTopico;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @PostMapping
    public void crearTopico(@RequestBody DatosCreacionTopico datosCreacionTopico)
    {
        System.out.println(datosCreacionTopico);
    }
}
