package com.alura.foro.dominio.topico;

import com.alura.foro.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public DatosMostrarTopico crearTopico(DatosCreacionTopico datosCreacionTopico, Usuario usuario)
    {
        var topico = new Topico(datosCreacionTopico, usuario);
        topicoRepository.save(topico);
        return new DatosMostrarTopico(topico);
    }
}
