CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    topico_id BIGINT NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    fecha DATETIME NOT NULL,
    usuario_id BIGINT NOT NULL,
    activo TINYINT(1) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (topico_id) REFERENCES topicos(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
