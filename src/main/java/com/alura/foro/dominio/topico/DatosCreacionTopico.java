package com.alura.foro.dominio.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCreacionTopico(

        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotNull
        Long usuarioId) {
}
