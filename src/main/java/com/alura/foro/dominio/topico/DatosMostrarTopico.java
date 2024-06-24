package com.alura.foro.dominio.topico;

import com.alura.foro.dominio.respuesta.DatosMostrarRespuesta;
import com.alura.foro.dominio.respuesta.Respuesta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DatosMostrarTopico(

        Long id,

        String titulo,

        String mensaje,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fecha,

        String nombreUsuario,

        //List<Respuesta> respuestas
        List<DatosMostrarRespuesta> respuestas
) {
    public DatosMostrarTopico(Topico t) {
        this(t.getId(), t.getTitulo(), t.getMensaje(), t.getFecha(), t.getUsuario().getNombre(),
                t.getRespuestas().stream().map(r -> new DatosMostrarRespuesta(r)).collect(Collectors.toList()));
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
