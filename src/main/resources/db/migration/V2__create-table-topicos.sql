CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    fecha DATETIME NOT NULL,
    usuario_id BIGINT NOT NULL,
    activo TINYINT(1) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
