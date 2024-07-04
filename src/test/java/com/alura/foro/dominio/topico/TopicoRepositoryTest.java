package com.alura.foro.dominio.topico;

import com.alura.foro.dominio.usuario.DatosCreacionUsuario;
import com.alura.foro.dominio.usuario.Rol;
import com.alura.foro.dominio.usuario.Usuario;
import com.alura.foro.dominio.usuario.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TopicoRepositoryTest {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Debería devolver false cuando no hay topicos activos con ese id")
    void findActivoByIdEscenario1TopicoInactivo() {

        //given
        Usuario usuario1 = registrarUsuario("Juan", "juan@mail.com", "123", Rol.USUARIO);

        Topico topicoInactivo = registrarTopico("Prueba", "Estoy probando", usuario1.getId());
        topicoInactivo.desactivarTopico();

        //when
        var topicoEstas = topicoRepository.findActivoById(topicoInactivo.getId());

        //then
        assertThat(!topicoEstas);
    }

    @Test
    @DisplayName("Debería devolver true cuando sí hay topicos activos con ese id")
    void findActivoByIdEscenario2TopicoActivo() {

        //given
        Usuario usuario2 = registrarUsuario("Juana", "juana@mail.com", "456", Rol.USUARIO);

        Topico topicoActivo = registrarTopico("Prueba", "Estoy probando", usuario2.getId());

        //when
        var topicoEstas = topicoRepository.findActivoById(topicoActivo.getId());

        //then
        assertThat(topicoEstas);
    }

    private Topico registrarTopico(String titulo, String mensaje, Long usuarioId)
    {
        Usuario usuario = new Usuario();
        var usuarioNuevo = usuarioRepository.findById(usuarioId);
        if(usuarioNuevo.isPresent())
        {
            usuario = usuarioNuevo.get();
        }

//        Usuario usuario = usuarioRepository.findById(usuarioId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid usuarioId: " + usuarioId));

        var topicoNuevo = new Topico(new DatosCreacionTopico(titulo, mensaje, usuarioId), usuario);
        testEntityManager.persist(topicoNuevo);
        return topicoNuevo;
    }

    private Usuario registrarUsuario(String nombre, String email, String password, Rol rol)
    {
        var usuarioNuevo = new Usuario(new DatosCreacionUsuario(nombre, email, password, rol));
        testEntityManager.persist(usuarioNuevo);
        return usuarioNuevo;
    }
}