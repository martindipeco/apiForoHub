package com.alura.foro.dominio.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAgregarRespuesta(

        @NotNull
        Long topicoId,

        @NotBlank
        String mensaje,

        @NotNull
        Long usuarioId
) {
}
