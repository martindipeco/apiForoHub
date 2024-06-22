package com.alura.foro.dominio.topico;

import com.alura.foro.dominio.usuario.DatosUsuario;

public record DatosCreacionTopico(String titulo, String mensaje, Long usuarioId) {
}
