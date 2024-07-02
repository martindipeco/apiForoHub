package com.alura.foro.infra.validacion;

import com.alura.foro.dominio.respuesta.DatosAgregarRespuesta;
import jakarta.validation.ValidationException;

public class RespuestaExtension implements ValidadorRespuesta{

    Integer MAXIMA_EXTENSION = 500;

    Integer MINIMA_EXTENSION = 1;
    public void validar (DatosAgregarRespuesta datosAgregarRespuesta)
    {
        var demasiadoLargo = datosAgregarRespuesta.mensaje().length() > MAXIMA_EXTENSION;

        var demasiadoCorto = datosAgregarRespuesta.mensaje().length() < MINIMA_EXTENSION;

        if (demasiadoLargo)
        {
            throw new ValidationException("Máxima cantidad de caracteres permitidos: " + MAXIMA_EXTENSION);
        }
        if (demasiadoCorto)
        {
            throw new ValidationException("Debe ingresar al menos " + MINIMA_EXTENSION + " caracteres");
        }
    }
}
