CREATE DATABASE IF NOT EXISTS instituto;

USE instituto;

CREATE TABLE IF NOT EXISTS alumnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,    
    edad INT NOT NULL
);

CREATE DATABASE IF NOT EXISTS instituto;

CREATE TABLE IF NOT EXISTS modulos (
    siglas VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    curso INT NOT NULL,
    horas INT NOT NULL
);

CREATE TABLE IF NOT EXISTS matricula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    alumno_id INT NOT NULL,
    modulo_siglas VARCHAR(10) NOT NULL,
    fecha_matricula DATE NOT NULL,
    curso_academico VARCHAR(10) NOT NULL,
    FOREIGN KEY (alumno_id) REFERENCES alumnos(id) ON DELETE CASCADE,
    FOREIGN KEY (modulo_siglas) REFERENCES modulos(siglas) ON DELETE CASCADE
);

INSERT INTO alumnos (nombre, apellidos, edad) VALUES
('Juan', 'Perez', 20),
('Maria', 'Gonzalez', 22),
('Carlos', 'Ramirez', 19),
('Ana', 'Lopez', 21),
('Luis', 'Martinez', 23);

INSERT INTO modulos (siglas, nombre, curso, horas) VALUES
('ISO', 'Implantación de Sistemas Operativos', 1, 140),
('SGBD', 'Sistemas de Gestión de Bases de Datos', 1, 150),
('PAR', 'Planificación y Administración de Redes', 1, 180),
('ASO', 'Administración de Sistemas Operativos', 2, 120),
('DI', 'Desarrollo de Interfaces', 2, 120);

INSERT INTO matricula (alumno_id, modulo_siglas, fecha_matricula, curso_academico) VALUES
-- Juan (ID 1)
(1, 'ISO', '2025-09-10', '25-26'),
(1, 'SGBD', '2025-09-10', '25-26'),
(1, 'PAR', '2025-09-10', '25-26'),
(1, 'ASO', '2025-09-10', '25-26'),
(1, 'DI', '2025-09-10', '25-26'),

-- Maria (ID 2)
(2, 'ISO', '2025-09-11', '25-26'),
(2, 'SGBD', '2025-09-11', '25-26'),
(2, 'PAR', '2025-09-11', '25-26'),
(2, 'ASO', '2025-09-11', '25-26'),
(2, 'DI', '2025-09-11', '25-26'),

-- Carlos (ID 3)
(3, 'ISO', '2025-09-12', '25-26'),
(3, 'SGBD', '2025-09-12', '25-26'),
(3, 'PAR', '2025-09-12', '25-26'),
(3, 'ASO', '2025-09-12', '25-26'),
(3, 'DI', '2025-09-12', '25-26'),

-- Ana (ID 4)
(4, 'ISO', '2025-09-10', '25-26'),
(4, 'SGBD', '2025-09-10', '25-26'),
(4, 'PAR', '2025-09-10', '25-26'),
(4, 'ASO', '2025-09-10', '25-26'),
(4, 'DI', '2025-09-10', '25-26'),

-- Luis (ID 5)
(5, 'ISO', '2025-09-13', '25-26'),
(5, 'SGBD', '2025-09-13', '25-26'),
(5, 'PAR', '2025-09-13', '25-26'),
(5, 'ASO', '2025-09-13', '25-26'),
(5, 'DI', '2025-09-13', '25-26');
