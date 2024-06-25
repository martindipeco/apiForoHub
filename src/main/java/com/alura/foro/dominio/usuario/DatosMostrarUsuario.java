package com.alura.foro.dominio.usuario;

public record DatosMostrarUsuario(Long id, String nombre) {

    public DatosMostrarUsuario(Usuario usuario)
    {
        this(usuario.getId(), usuario.getNombre());
    }
}
