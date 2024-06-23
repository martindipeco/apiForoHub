package com.alura.foro.dominio.respuesta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DatosMostrarRespuesta(

        String mensaje,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fecha,

        String nombreUsuario
) {

    public DatosMostrarRespuesta(Respuesta respuesta)
    {
        this(respuesta.getMensaje(), respuesta.getFecha(), respuesta.getUsuario().getNombre());
    }
}
