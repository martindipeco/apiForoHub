package com.alura.foro.dominio.topico;

import com.alura.foro.dominio.respuesta.Respuesta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record DatosMostrarTopico(

        String titulo,

        String mensaje,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fecha,

        String nombreUsuario,

        List<Respuesta> respuestas
) {
    public DatosMostrarTopico(Topico t) {
        this(t.getTitulo(), t.getMensaje(), t.getFecha(), t.getUsuario().getNombre(), t.getRespuestas());
    }

    //forma incorrecta
//    public DatosMostrarTopico(Topico t)
//    {
//        this.titulo = t.getTitulo();
//        this.mensaje = t.getMensaje();
//        this.fecha = t.getFecha();
//        this.nombreUsuario = t.getUsuario().getNombre();
//    }
}
