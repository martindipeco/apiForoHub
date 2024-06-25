package com.alura.foro.dominio.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCreacionUsuario(
        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotNull
        Rol rol) {
}
