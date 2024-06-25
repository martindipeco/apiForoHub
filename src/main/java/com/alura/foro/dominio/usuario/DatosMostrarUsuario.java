package com.alura.foro.dominio.usuario;

public record DatosMostrarUsuario(Long id, String nombre, Rol rol) {

    public DatosMostrarUsuario(Usuario usuario)
    {
        this(usuario.getId(), usuario.getNombre(), usuario.getRol());
    }
}
