package com.alura.foro.dominio.respuesta;

import com.alura.foro.dominio.topico.Topico;
import com.alura.foro.dominio.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) //muchas respuestas a un topico
    @JoinColumn(name = "topico_id")
    private Topico topico;
    private String mensaje;
    private LocalDateTime fecha;
    @ManyToOne(fetch = FetchType.LAZY) //muchas respuestas a un usuario
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private boolean activo;

    public void setTopico(Topico topico) {
        this.topico = topico;
    }


}
