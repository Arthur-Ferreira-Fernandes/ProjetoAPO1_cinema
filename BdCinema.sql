DROP DATABASE IF EXISTS cinema;
CREATE DATABASE cinema;
USE cinema;

-- =============================================
-- 1. CRIAÇÃO DAS TABELAS
-- =============================================

CREATE TABLE Filme (
    FilmeId INT PRIMARY KEY AUTO_INCREMENT,
    Titulo TEXT (255) NOT NULL,
    Duracao INT NOT NULL,
    Genero TEXT (255) NOT NULL,
    Classificacao TEXT (100) NOT NULL,
    Sinopse LONGTEXT NOT NULL
);

CREATE TABLE Sala (
    SalaId INT PRIMARY KEY AUTO_INCREMENT,
    NumeroSala INT NOT NULL,
    Capacidade INT NOT NULL,
    TipoSala TEXT (100) NOT NULL,
    Disponivel BOOL NOT NULL DEFAULT(0)
);

CREATE TABLE Sessao (
    SessaoId INT PRIMARY KEY AUTO_INCREMENT,
    HorarioInicio DATETIME NOT NULL,
    HorarioFim DATETIME NOT NULL,
    FilmeId INT,
    SalaId INT,
    FOREIGN KEY (FilmeId) REFERENCES Filme(FilmeId),
    FOREIGN KEY (SalaId) REFERENCES Sala(SalaId)
);

CREATE TABLE Limpeza (
    LimpezaId INT PRIMARY KEY AUTO_INCREMENT,
    DataLimpeza DATETIME NOT NULL,
    StatusLimpeza TEXT (100) NOT NULL,
    ObservacaoLimpeza LONGTEXT,
    SalaId INT,
    FOREIGN KEY (SalaId) REFERENCES Sala(SalaId)
);

CREATE TABLE Manutencao (
    ManutencaoId INT PRIMARY KEY AUTO_INCREMENT,
    DataManutencao DATETIME NOT NULL,
    StatusManutencao TEXT (100) NOT NULL,
    ObservacaoManutencao LONGTEXT,
    SalaId INT,
    FOREIGN KEY (SalaId) REFERENCES Sala(SalaId)
);

CREATE TABLE ControladorDeAcesso (
    ContAcessId INT PRIMARY KEY AUTO_INCREMENT,
    ContAcessNome TEXT(255) NOT NULL
);

CREATE TABLE ControladorDeSala (
    ContSalaId INT PRIMARY KEY AUTO_INCREMENT,
    ContSalaNome TEXT(255) NOT NULL,
    SalaId INT,
    FOREIGN KEY (SalaId) REFERENCES Sala(SalaId)
);

CREATE TABLE Poltrona (
    PoltronaId INT PRIMARY KEY AUTO_INCREMENT,
    PoltronaNumero TEXT(100) NOT NULL,
    Disponibilidade BOOL NOT NULL DEFAULT(0),
    SalaId INT,
    FOREIGN KEY (SalaId) REFERENCES Sala(SalaId)
);

CREATE TABLE Bilheteria (
    BilheteriaId INT PRIMARY KEY AUTO_INCREMENT,
    BilheteriaNome TEXT(255) NOT NULL
);

CREATE TABLE Cliente (
    ClienteId INT PRIMARY KEY AUTO_INCREMENT,
    NomeCliente TEXT (255) NOT NULL,
    Email TEXT NOT NULL,
    Telefone TEXT NOT NULL
);

CREATE TABLE Ingresso (
    IngressoId INT PRIMARY KEY AUTO_INCREMENT,
    StatusIngresso TEXT (100) NOT NULL,
    DataCompra DATETIME NOT NULL,
    ClienteId INT,
    SessaoId INT,
    PoltronaId INT,
    FOREIGN KEY (ClienteId) REFERENCES Cliente(ClienteId),
    FOREIGN KEY (SessaoId) REFERENCES Sessao(SessaoId),
    FOREIGN KEY (PoltronaId) REFERENCES Poltrona(PoltronaId)
);

-- =============================================
-- 2. INSERÇÃO DE DADOS (CARGA INICIAL)
-- =============================================

INSERT INTO Filme (Titulo, Duracao, Genero, Classificacao, Sinopse) VALUES
('Vingadores: Ultimato', 181, 'Ação', '14 anos', 'Os heróis restantes lutam para reverter o estalar de dedos de Thanos.'),
('Interestelar', 169, 'Ficção Científica', '10 anos', 'Um grupo viaja pelo espaço em busca de um novo lar para a humanidade.'),
('Toy Story 4', 100, 'Animação', 'Livre', 'Woody e Buzz vivem novas aventuras com um novo brinquedo.'),
('O Exorcista', 122, 'Terror', '18 anos', 'Uma jovem é possuída e dois padres tentam salvá-la.');

INSERT INTO Sala (NumeroSala, Capacidade, TipoSala, Disponivel) VALUES
(1, 100, '2D', 1),
(2, 150, '3D', 1),
(3, 80, 'VIP', 1);

INSERT INTO Limpeza (DataLimpeza, StatusLimpeza, ObservacaoLimpeza, SalaId) VALUES
(NOW(), 'Concluída', 'Limpeza geral antes das sessões', 1),
(NOW(), 'Pendente', 'Aguardando equipe', 2),
(NOW(), 'Concluída', 'Sala higienizada após sessão anterior', 3);

INSERT INTO Manutencao (DataManutencao, StatusManutencao, ObservacaoManutencao, SalaId) VALUES
(NOW(), 'Normal', 'Verificação de rotina', 1),
(NOW(), 'Em manutenção', 'Problema no projetor', 2),
(NOW(), 'Normal', 'Ar-condicionado ajustado', 3);

INSERT INTO ControladorDeAcesso (ContAcessNome) VALUES
('João Menezes'),
('Carla Santos'),
('Rafael Souza');

INSERT INTO ControladorDeSala (ContSalaNome, SalaId) VALUES
('Pedro Almeida', 1),
('Ana Paula', 2),
('Marcos Lima', 3);

-- Sala 1
INSERT INTO Poltrona (PoltronaNumero, Disponibilidade, SalaId) VALUES
('A1', 1, 1), ('A2', 1, 1), ('A3', 1, 1), ('A4', 1, 1), ('A5', 1, 1),
('B1', 1, 1), ('B2', 1, 1), ('B3', 1, 1), ('B4', 1, 1), ('B5', 1, 1);

-- Sala 2
INSERT INTO Poltrona (PoltronaNumero, Disponibilidade, SalaId) VALUES
('A1', 1, 2), ('A2', 1, 2), ('A3', 1, 2), ('A4', 1, 2), ('A5', 1, 2),
('B1', 1, 2), ('B2', 1, 2), ('B3', 1, 2), ('B4', 1, 2), ('B5', 1, 2);

-- Sala 3
INSERT INTO Poltrona (PoltronaNumero, Disponibilidade, SalaId) VALUES
('A1', 1, 3), ('A2', 1, 3), ('A3', 1, 3), ('A4', 1, 3), ('A5', 1, 3),
('B1', 1, 3), ('B2', 1, 3), ('B3', 1, 3), ('B4', 1, 3), ('B5', 1, 3);

INSERT INTO Bilheteria (BilheteriaNome) VALUES
('Bilheteria Principal'),
('Bilheteria Leste'),
('Bilheteria VIP');

INSERT INTO Cliente (NomeCliente, Email, Telefone) VALUES
('Arthur Ferreira', 'arthur@email.com', '11999990000'),
('Maria Silva', 'maria@email.com', '11988887777'),
('João Pereira', 'joao@email.com', '11977776666');

INSERT INTO Sessao (HorarioInicio, HorarioFim, FilmeId, SalaId) VALUES
('2025-12-01 14:00:00', '2025-12-01 17:00:00', 1, 1), 
('2025-12-01 18:00:00', '2025-12-01 21:00:00', 2, 2), 
('2025-12-02 13:00:00', '2025-12-02 15:00:00', 3, 3), 
('2025-12-02 22:00:00', '2025-12-03 00:00:00', 4, 1);

INSERT INTO Ingresso (StatusIngresso, DataCompra, ClienteId, SessaoId, PoltronaId) VALUES
('Pago', NOW(), 1, 1, 1),
('Pago', NOW(), 2, 2, 12),
('Reservado', NOW(), 3, 3, 23),
('Cancelado', NOW(), 1, 4, 5);

-- =============================================
-- 3. STORED PROCEDURES (NOVA LÓGICA)
-- =============================================

DELIMITER $$

-- ---------------------------------------------------------
-- PROCEDURE: Comprar Ingresso (Transação Segura)
-- ---------------------------------------------------------
CREATE PROCEDURE sp_ComprarIngresso(
    IN p_ClienteId INT,
    IN p_SessaoId INT,
    IN p_PoltronaId INT,
    IN p_Status VARCHAR(50),
    OUT p_IngressoId INT
)
BEGIN
    DECLARE v_Disponivel BOOLEAN;

    -- Inicia transação
    START TRANSACTION;

    -- Verifica disponibilidade e bloqueia a linha (Evita venda dupla)
    SELECT Disponibilidade INTO v_Disponivel 
    FROM Poltrona 
    WHERE PoltronaId = p_PoltronaId 
    FOR UPDATE;

    IF v_Disponivel = 1 THEN
        -- Marca poltrona como ocupada (0)
        UPDATE Poltrona SET Disponibilidade = 0 WHERE PoltronaId = p_PoltronaId;

        -- Insere o ingresso
        INSERT INTO Ingresso (StatusIngresso, DataCompra, ClienteId, SessaoId, PoltronaId)
        VALUES (p_Status, NOW(), p_ClienteId, p_SessaoId, p_PoltronaId);

        -- Retorna o ID gerado e confirma
        SET p_IngressoId = LAST_INSERT_ID();
        COMMIT;
    ELSE
        -- Poltrona já ocupada, cancela tudo
        ROLLBACK;
        SET p_IngressoId = -1;
    END IF;
END $$

-- ---------------------------------------------------------
-- PROCEDURE: Cancelar Reserva
-- ---------------------------------------------------------
CREATE PROCEDURE sp_CancelarReserva(
    IN p_IngressoId INT,
    OUT p_Resultado VARCHAR(100)
)
BEGIN
    DECLARE v_PoltronaId INT;
    DECLARE v_StatusAtual VARCHAR(100);

    START TRANSACTION;

    -- Busca status e poltrona associada
    SELECT StatusIngresso, PoltronaId INTO v_StatusAtual, v_PoltronaId
    FROM Ingresso WHERE IngressoId = p_IngressoId
    FOR UPDATE;

    IF v_StatusAtual IS NULL THEN
        ROLLBACK;
        SET p_Resultado = 'Reserva não encontrada.';
    ELSEIF v_StatusAtual = 'CANCELADA' THEN
        ROLLBACK;
        SET p_Resultado = 'Reserva já estava cancelada.';
    ELSE
        -- Atualiza status do ingresso
        UPDATE Ingresso SET StatusIngresso = 'CANCELADA' WHERE IngressoId = p_IngressoId;

        -- Libera a poltrona (Disponibilidade = 1)
        UPDATE Poltrona SET Disponibilidade = 1 WHERE PoltronaId = v_PoltronaId;

        COMMIT;
        SET p_Resultado = 'Reserva cancelada com sucesso!';
    END IF;
END $$

-- ---------------------------------------------------------
-- PROCEDURES: Limpeza
-- ---------------------------------------------------------
CREATE PROCEDURE sp_IniciarLimpeza(IN p_SalaId INT)
BEGIN
    INSERT INTO Limpeza (DataLimpeza, StatusLimpeza, SalaId) 
    VALUES (NOW(), 'EM ANDAMENTO', p_SalaId);
END $$

CREATE PROCEDURE sp_FinalizarLimpeza(IN p_SalaId INT)
BEGIN
    -- Atualiza qualquer limpeza em andamento para concluído
    UPDATE Limpeza 
    SET StatusLimpeza = 'concluido' 
    WHERE SalaId = p_SalaId AND StatusLimpeza = 'EM ANDAMENTO';

    -- Cria o registro de log de conclusão
    INSERT INTO Limpeza (DataLimpeza, StatusLimpeza, SalaId) 
    VALUES (NOW(), 'concluido', p_SalaId);
END $$

-- ---------------------------------------------------------
-- PROCEDURES: Manutenção
-- ---------------------------------------------------------
CREATE PROCEDURE sp_IniciarManutencao(IN p_SalaId INT)
BEGIN
    INSERT INTO Manutencao (DataManutencao, StatusManutencao, SalaId) 
    VALUES (NOW(), 'EM ANDAMENTO', p_SalaId);
END $$

CREATE PROCEDURE sp_FinalizarManutencao(IN p_SalaId INT)
BEGIN
    -- Atualiza manutenção em andamento para concluído
    UPDATE Manutencao 
    SET StatusManutencao = 'concluido' 
    WHERE SalaId = p_SalaId AND StatusManutencao = 'EM ANDAMENTO';

    -- Cria o registro de log de conclusão
    INSERT INTO Manutencao (DataManutencao, StatusManutencao, SalaId) 
    VALUES (NOW(), 'concluido', p_SalaId);
END $$

DELIMITER ;