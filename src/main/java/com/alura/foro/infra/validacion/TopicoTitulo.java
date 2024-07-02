package com.alura.foro.infra.validacion;

import com.alura.foro.dominio.topico.DatosCreacionTopico;
import jakarta.validation.ValidationException;

public class TopicoTitulo implements ValidadorTopico{

    Integer MAXIMA_EXTENSION = 100;

    Integer MINIMA_EXTENSION = 1;
    public void validar (DatosCreacionTopico datosCreacionTopico)
    {
        var demasiadoLargo = datosCreacionTopico.titulo().length() > MAXIMA_EXTENSION;

        var demasiadoCorto = datosCreacionTopico.titulo().length() < MINIMA_EXTENSION;

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
