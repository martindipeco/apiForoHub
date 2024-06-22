package com.alura.foro.dominio.topico;

import com.alura.foro.dominio.respuesta.Respuesta;
import com.alura.foro.dominio.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha;
    @ManyToOne(fetch = FetchType.LAZY) //muchos topicos a un usuario
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private boolean activo;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas;

    public Topico(DatosCreacionTopico datosCreacionTopico, Usuario usuario) {
        this.titulo = datosCreacionTopico.titulo();
        this.mensaje = datosCreacionTopico.mensaje();
        this.fecha = LocalDateTime.now();
        this.usuario = usuario;
        this.activo = true;
    }

    public void addRespuesta(Respuesta respuesta) {
        respuestas.add(respuesta);
        respuesta.setTopico(this);
    }

    public void removeRespuesta(Respuesta respuesta) {
        respuestas.remove(respuesta);
        respuesta.setTopico(null);
    }
}
