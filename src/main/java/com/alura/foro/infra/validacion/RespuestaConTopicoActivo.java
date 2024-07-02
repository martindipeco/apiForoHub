package com.alura.foro.infra.validacion;

import com.alura.foro.dominio.respuesta.DatosAgregarRespuesta;
import com.alura.foro.dominio.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaConTopicoActivo implements ValidadorRespuesta{

    @Autowired
    private TopicoRepository topicoRepository;
    public void validar (DatosAgregarRespuesta datosAgregarRespuesta)
    {
        if (datosAgregarRespuesta.topicoId() == null)
        {
            return;
        }
        var topicoActivo = topicoRepository.findActivoById(datosAgregarRespuesta.topicoId());

        if(!topicoActivo)
        {
            throw new ValidationException("Topico no está activo");
        }
    }
}
