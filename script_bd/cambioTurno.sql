-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS cambioturno;
USE cambioturno;

-- Creación de la tabla 'perfiles'
CREATE TABLE perfiles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    info VARCHAR(100) NOT NULL
) ENGINE=INNODB;

-- Creación de la tabla 'usuarios'
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    pass VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    ultima_conexion DATETIME NOT NULL,
    estatus INT NOT NULL,
    online INT NOT NULL
) ENGINE=INNODB;

-- Creación de la tabla 'usuarioperfil'
CREATE TABLE usuarioperfil (
    idUsuario INT NOT NULL,
    idPerfil INT NOT NULL,
    PRIMARY KEY (idUsuario, idPerfil),
    FOREIGN KEY (idUsuario) REFERENCES usuarios (id),
    FOREIGN KEY (idPerfil) REFERENCES perfiles (id)
) ENGINE=INNODB;

-- Creación de la tabla 'cambios_turno'
CREATE TABLE cambios_turno (
    id INT PRIMARY KEY AUTO_INCREMENT,
    solicitante_id INT NOT NULL,
    aceptante VARCHAR(255) NOT NULL,
    fecha_cambio VARCHAR(255) NOT NULL,
    turno_a_cambiar VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL, -- Por ejemplo: 'Pendiente', 'Aceptado', 'Rechazado'
    fecha_solicitud DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion DATETIME,
    FOREIGN KEY (solicitante_id) REFERENCES usuarios (id)
) ENGINE=INNODB;

-- Creación de la tabla 'notificaciones'
CREATE TABLE notificaciones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    usuarioId INT NOT NULL,
    fechaHora DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL,
    referenciaId INT NOT NULL,
    FOREIGN KEY (usuarioId) REFERENCES usuarios(id),
    INDEX (tipo),
    INDEX (fechaHora),
    INDEX (estado)
) ENGINE=InnoDB;

-- Inserción de datos en la tabla 'perfiles'
INSERT INTO perfiles (id, nombre, info) VALUES
(900, 'Administrador', 'Administrador del sistema'),
(500, 'Operador', 'Operador del sistema'),
(300, 'Celador', 'Celador del sistema');

-- Inserción de datos en la tabla 'usuarios'
INSERT INTO usuarios (id, nombre, username, email, pass, fecha_registro, ultima_conexion, estatus, online) VALUES
(1, 'Admin1', 'admin', 'admin1@example.com', '$2b$12$c20TLvVOzHm0GSxFu0rQGOZkkFOzEhp2p8XN5QuSmsCinPt5gBvD2', NOW(), NOW(), 1, 0),
(2, 'Admin2', 'admin2', 'admin2@example.com', '$2b$12$c20TLvVOzHm0GSxFu0rQGOZkkFOzEhp2p8XN5QuSmsCinPt5gBvD2', NOW(), NOW(), 1, 0),
(3, 'Operador1', 'ope1', 'operador1@example.com', '$2b$12$c20TLvVOzHm0GSxFu0rQGOZkkFOzEhp2p8XN5QuSmsCinPt5gBvD2', NOW(), NOW(), 1, 0),
(4, 'Operador2', 'ope2', 'operador2@example.com', '$2b$12$c20TLvVOzHm0GSxFu0rQGOZkkFOzEhp2p8XN5QuSmsCinPt5gBvD2', NOW(), NOW(), 1, 0),
(5, 'Celador1', 'cel1', 'celador1@example.com', '$2b$12$c20TLvVOzHm0GSxFu0rQGOZkkFOzEhp2p8XN5QuSmsCinPt5gBvD2', NOW(), NOW(), 1, 0);

-- Inserción de datos en la tabla 'usuarioperfil'
INSERT INTO usuarioperfil (idUsuario, idPerfil) VALUES
(1, 900),
(2, 900),
(3, 500),
(4, 500),
(5, 300);

-- Inserción de datos en la tabla 'cambios_turno'
INSERT INTO cambios_turno (solicitante_id, aceptante, fecha_cambio, turno_a_cambiar, estado, fecha_resolucion) VALUES
(3, 'aceptante1', '2024-06-01', 'Cambio de turno de mañana a tarde', 'Pendiente', NULL),
(4, 'aceptante2', '2024-06-02', 'Cambio de turno de tarde a noche', 'Pendiente', NULL),
(5, 'aceptante3', '2024-06-03', 'Cambio de turno de noche a mañana', 'Pendiente', NULL),
(3, 'aceptante4', '2024-06-04', 'Cambio de turno de mañana a tarde', 'Pendiente', NULL),
(4, 'aceptante5', '2024-06-05', 'Cambio de turno de tarde a noche', 'Pendiente', NULL),
(5, 'aceptante6', '2024-06-06', 'Cambio de turno de noche a mañana', 'Pendiente', NULL),
(3, 'aceptante7', '2024-06-07', 'Cambio de turno de mañana a tarde', 'Pendiente', NULL),
(4, 'aceptante8', '2024-06-08', 'Cambio de turno de tarde a noche', 'Pendiente', NULL),
(5, 'aceptante9', '2024-06-09', 'Cambio de turno de noche a mañana', 'Pendiente', NULL),
(3, 'aceptante10', '2024-06-10', 'Cambio de turno de mañana a tarde', 'Pendiente', NULL),
(4, 'aceptante11', '2024-06-11', 'Cambio de turno de tarde a noche', 'Pendiente', NULL),
(5, 'aceptante12', '2024-06-12', 'Cambio de turno de noche a mañana', 'Pendiente', NULL),
(3, 'aceptante13', '2024-06-13', 'Cambio de turno de mañana a tarde', 'Pendiente', NULL),
(4, 'aceptante14', '2024-06-14', 'Cambio de turno de tarde a noche', 'Pendiente', NULL),
(5, 'aceptante15', '2024-06-15', 'Cambio de turno de noche a mañana', 'Pendiente', NULL),
(3, 'aceptante16', '2024-06-16', 'Cambio de turno de mañana a tarde', 'Pendiente', NULL),
(4, 'aceptante17', '2024-06-17', 'Cambio de turno de tarde a noche', 'Pendiente', NULL),
(5, 'aceptante18', '2024-06-18', 'Cambio de turno de noche a mañana', 'Pendiente', NULL),
(3, 'aceptante19', '2024-06-19', 'Cambio de turno de mañana a tarde', 'Pendiente', NULL),
(4, 'aceptante20', '2024-06-20', 'Cambio de turno de tarde a noche', 'Pendiente', NULL);

-- Inserción de datos en la tabla 'notificaciones'
INSERT INTO notificaciones (tipo, descripcion, usuarioId, fechaHora, estado, referenciaId) VALUES
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador1', 1, NOW(), 'No leído', 1),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador2', 1, NOW(), 'No leído', 2),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Celador1', 2, NOW(), 'No leído', 3),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador1', 1, NOW(), 'No leído', 4),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador2', 2, NOW(), 'No leído', 5),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Celador1', 1, NOW(), 'No leído', 6),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador1', 1, NOW(), 'No leído', 7),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador2', 2, NOW(), 'No leído', 8),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Celador1', 1, NOW(), 'No leído', 9),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador1', 1, NOW(), 'No leído', 10),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador2', 2, NOW(), 'No leído', 11),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Celador1', 1, NOW(), 'No leído', 12),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador1', 1, NOW(), 'No leído', 13),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador2', 2, NOW(), 'No leído', 14),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Celador1', 1, NOW(), 'No leído', 15),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador1', 1, NOW(), 'No leído', 16),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador2', 2, NOW(), 'No leído', 17),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Celador1', 1, NOW(), 'No leído', 18),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador1', 1, NOW(), 'No leído', 19),
('Cambio de Turno', 'Nuevo cambio de turno solicitado por Operador2', 2, NOW(), 'No leído', 20);
