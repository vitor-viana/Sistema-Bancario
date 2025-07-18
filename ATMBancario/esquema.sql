CREATE TABLE usuarios (
    id_usuario INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL
);

CREATE TABLE contas (
    id_conta INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_usuario INTEGER NOT NULL,
    numero_conta VARCHAR(20) NOT NULL UNIQUE,
    saldo DECIMAL(10, 2) DEFAULT 0.00,
    tipo_conta VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE transacoes (
    id_transacao INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_conta_origem INTEGER NOT NULL,
    id_conta_destino INTEGER,
    valor DECIMAL(10, 2) NOT NULL,
    tipo_transacao VARCHAR(20) NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_conta_origem) REFERENCES contas(id_conta),
    FOREIGN KEY (id_conta_destino) REFERENCES contas(id_conta)
);

