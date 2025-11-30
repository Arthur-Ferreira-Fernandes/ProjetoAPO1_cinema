CREATE DATABASE cinema;

USE cinema;

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

INSERT INTO Poltrona (PoltronaNumero, Disponibilidade, SalaId) VALUES
('A1', 1, 1), ('A2', 1, 1), ('A3', 1, 1), ('A4', 1, 1), ('A5', 1, 1),
('B1', 1, 1), ('B2', 1, 1), ('B3', 1, 1), ('B4', 1, 1), ('B5', 1, 1);

INSERT INTO Poltrona (PoltronaNumero, Disponibilidade, SalaId) VALUES
('A1', 1, 2), ('A2', 1, 2), ('A3', 1, 2), ('A4', 1, 2), ('A5', 1, 2),
('B1', 1, 2), ('B2', 1, 2), ('B3', 1, 2), ('B4', 1, 2), ('B5', 1, 2);

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
('2025-12-01 14:00:00', '2025-12-01 17:00:00', 1, 1), -- Vingadores
('2025-12-01 18:00:00', '2025-12-01 21:00:00', 2, 2), -- Interestelar
('2025-12-02 13:00:00', '2025-12-02 15:00:00', 3, 3), -- Toy Story
('2025-12-02 22:00:00', '2025-12-03 00:00:00', 4, 1); -- O Exorcista

INSERT INTO Ingresso (StatusIngresso, DataCompra, ClienteId, SessaoId, PoltronaId) VALUES
('Pago', NOW(), 1, 1, 1),
('Pago', NOW(), 2, 2, 12),
('Reservado', NOW(), 3, 3, 23),
('Cancelado', NOW(), 1, 4, 5);

