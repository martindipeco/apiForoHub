package com.alura.foro.infra.validacion;

import com.alura.foro.dominio.topico.DatosCreacionTopico;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TopicoExtension implements ValidadorTopico{

    Integer MAXIMA_EXTENSION = 500;

    Integer MINIMA_EXTENSION = 2;
    public void validar (DatosCreacionTopico datosCreacionTopico)
    {
        var demasiadoLargo = datosCreacionTopico.mensaje().length() > MAXIMA_EXTENSION;

        var demasiadoCorto = datosCreacionTopico.mensaje().length() < MINIMA_EXTENSION;

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
