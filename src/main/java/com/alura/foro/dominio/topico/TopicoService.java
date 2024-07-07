package com.alura.foro.dominio.topico;

import com.alura.foro.dominio.respuesta.DatosAgregarRespuesta;
import com.alura.foro.dominio.respuesta.Respuesta;
import com.alura.foro.dominio.usuario.Usuario;
import com.alura.foro.dominio.usuario.UsuarioRepository;
import com.alura.foro.infra.exception.ValidacionIntegridadException;
import com.alura.foro.infra.validacion.ValidadorRespuesta;
import com.alura.foro.infra.validacion.ValidadorTopico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    List<ValidadorTopico> validadoresTopico;

    @Autowired
    List<ValidadorRespuesta> validadoresRespuesta;

    public DatosMostrarTopico crearTopico(DatosCreacionTopico datosCreacionTopico)
    {
        //Validación de integridad
        if (usuarioRepository.findById(datosCreacionTopico.usuarioId()).isEmpty())
        {
            throw new ValidacionIntegridadException("No se encontró usuario con ese ID");
        }

        //validaciones
        validadoresTopico.forEach(v -> v.validar(datosCreacionTopico));

        var usuario = usuarioRepository.getReferenceById(datosCreacionTopico.usuarioId());
        Topico topico = new Topico(datosCreacionTopico, usuario);
        topicoRepository.save(topico);

        return new DatosMostrarTopico(topico);
    }

    public DatosMostrarTopico agregarRespuesta(DatosAgregarRespuesta datosAgregarRespuesta)
    {
        //Validación de integridad
        if (usuarioRepository.findById(datosAgregarRespuesta.usuarioId()).isEmpty())
        {
            throw new ValidacionIntegridadException("No se encontró usuario con ese ID");
        }

        var usuario = usuarioRepository.getReferenceById(datosAgregarRespuesta.usuarioId());

        //Validación de integridad
        if (topicoRepository.findById(datosAgregarRespuesta.topicoId()).isEmpty())
        {
            throw new ValidacionIntegridadException("No se encontró tópico con ese ID");
        }

        var topicoParaEditar = topicoRepository.getReferenceById(datosAgregarRespuesta.topicoId());

        //validaciones
        validadoresRespuesta.forEach(v -> v.validar(datosAgregarRespuesta));

        var respuestaNueva = new Respuesta(datosAgregarRespuesta, topicoParaEditar, usuario);
        topicoParaEditar.addRespuesta(respuestaNueva);
        topicoRepository.save(topicoParaEditar);

        return new DatosMostrarTopico(topicoParaEditar);
    }
}
