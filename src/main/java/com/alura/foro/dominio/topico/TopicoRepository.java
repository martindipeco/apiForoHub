package com.alura.foro.dominio.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByActivoTrue(Pageable paginacion);

    @Query("""
            SELECT t.activo
            FROM Topico t
            WHERE t.id = :idTopico
            """)
    Boolean findActivoById(Long idTopico);
}
