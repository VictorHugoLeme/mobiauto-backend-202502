CREATE TABLE revenda (
    id BIGINT PRIMARY KEY,
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    nome_social VARCHAR(255) NOT NULL
);

CREATE TABLE usuario (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    revenda_id BIGINT,
    FOREIGN KEY (revenda_id) REFERENCES revenda(id)
);

CREATE TABLE oportunidade (
    id BIGINT PRIMARY KEY,
    cliente_nome VARCHAR(255) NOT NULL,
    cliente_email VARCHAR(255),
    cliente_telefone VARCHAR(20),
    veiculo_marca VARCHAR(255),
    veiculo_modelo VARCHAR(50),
    veiculo_versao VARCHAR(10),
    veiculo_ano VARCHAR(9),
    status VARCHAR(50) NOT NULL DEFAULT 'NOVO',
    data_atribuicao TIMESTAMP,
    data_conclusao TIMESTAMP,
    motivo_conclusao TEXT,
    usuario_id BIGINT,
    revenda_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (revenda_id) REFERENCES revenda(id)
);