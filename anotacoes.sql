DROP DATABASE IF EXISTS anotacoes;

CREATE DATABASE anotacoes;

\c anotacoes;

CREATE TABLE anotacao (
    id SERIAL AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    cor VARCHAR(50),
    foto BYTEA,
    data_hora_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);