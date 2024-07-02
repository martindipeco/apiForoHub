package com.alura.foro.infra.validacion;

import com.alura.foro.dominio.respuesta.DatosAgregarRespuesta;
import com.alura.foro.dominio.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaConUsuarioActivo implements ValidadorRespuesta{

    @Autowired
    private UsuarioRepository usuarioRepository;
    public void validar (DatosAgregarRespuesta datosAgregarRespuesta)
    {
        if (datosAgregarRespuesta.usuarioId() == null)
        {
            return;
        }
        var usuarioActivo = usuarioRepository.findActivoById(datosAgregarRespuesta.usuarioId());

        if(!usuarioActivo)
        {
            throw new ValidationException("Usuario no está activo");
        }
    }
}
