package com.alura.foro.infra.validacion;

import com.alura.foro.dominio.respuesta.DatosAgregarRespuesta;

public interface ValidadorRespuesta {

    public void validar(DatosAgregarRespuesta datosAgregarRespuesta);
}
