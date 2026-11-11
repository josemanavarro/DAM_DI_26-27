-- Configuración de codificación para la sesión y el cliente
SET NAMES 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';
SET CHARACTER SET utf8mb4;

-- Base de datos para el sistema de gestión del instituto
CREATE DATABASE IF NOT EXISTS instituto CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE instituto;
SET collation_connection = 'utf8mb4_unicode_ci';

-- Eliminación previa de tablas para garantizar una recarga limpia y sin conflictos
DROP TABLE IF EXISTS matricula;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS modulos;
DROP TABLE IF EXISTS alumnos;

-- Tabla de Usuarios del sistema (autenticación)
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Alumnos
CREATE TABLE alumnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    edad INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Módulos profesionales
CREATE TABLE modulos (
    siglas VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    curso INT NOT NULL,
    horas INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Matrículas (relación alumno - módulo)
CREATE TABLE matricula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    alumno_id INT NOT NULL,
    modulo_siglas VARCHAR(10) NOT NULL,
    fecha_matricula DATE NOT NULL,
    curso_academico VARCHAR(10) NOT NULL,
    FOREIGN KEY (alumno_id) REFERENCES alumnos(id) ON DELETE CASCADE,
    FOREIGN KEY (modulo_siglas) REFERENCES modulos(siglas) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -------------------------------------------------------------
-- Datos iniciales: Usuarios de prueba
-- -------------------------------------------------------------
INSERT INTO usuarios (login, password) VALUES
('admin', 'admin'),
('profesor', 'profesor123');

-- -------------------------------------------------------------
-- Datos iniciales: Alumnos (15 en total: 5 existentes + 10 nuevos)
-- -------------------------------------------------------------
INSERT INTO alumnos (nombre, apellidos, edad) VALUES
('Juan', 'Pérez García', 20),
('María', 'González López', 22),
('Carlos', 'Ramírez Sánchez', 19),
('Ana', 'López Fernández', 21),
('Luis', 'Martínez Moreno', 23),
('Elena', 'Fernández Ruiz', 24),
('David', 'Sanz Molina', 20),
('Lucía', 'Moreno Díaz', 21),
('Javier', 'Gómez Navarro', 22),
('Paula', 'Jiménez Castro', 19),
('Alejandro', 'Torres Serrano', 25),
('Sofía', 'Romero Martín', 20),
('Manuel', 'Rubio Delgado', 23),
('Carmen', 'Álvarez Gil', 22),
('Pablo', 'Ortiz Morales', 21);

-- -------------------------------------------------------------
-- Datos iniciales: Módulos profesionales de ASIR y DAM
-- -------------------------------------------------------------
INSERT INTO modulos (siglas, nombre, curso, horas) VALUES
-- Módulos de 1º ASIR
('ISO', 'Implantación de Sistemas Operativos', 1, 140),
('PAR', 'Planificación y Administración de Redes', 1, 180),
('SGBD', 'Sistemas de Gestión de Bases de Datos', 1, 150),
('FH', 'Fundamentos de Hardware', 1, 100),
('LMSGI', 'Lenguajes de Marcas y Sistemas de Gestión de Información', 1, 130),
('FOL', 'Formación y Orientación Laboral', 1, 90),

-- Módulos de 2º ASIR
('ASO', 'Administración de Sistemas Operativos', 2, 120),
('SRI', 'Servicios de Red e Internet', 2, 140),
('IAW', 'Implantación de Aplicaciones Web', 2, 120),
('SAD', 'Seguridad y Alta Disponibilidad', 2, 100),
('EIE', 'Empresa e Iniciativa Emprendedora', 2, 60),
('HLC-ASIR', 'Horas de Libre Configuración (ASIR)', 2, 60),
('PASIR', 'Proyecto de Administración de Sistemas Informáticos en Red', 2, 40),
('FCT-ASIR', 'Formación en Centros de Trabajo (ASIR)', 2, 400),

-- Módulos específicos de 1º DAM (además de LMSGI y FOL que son comunes)
('SI', 'Sistemas Informáticos', 1, 160),
('BBDD', 'Bases de Datos', 1, 200),
('PROG', 'Programación', 1, 260),
('ED', 'Entornos de Desarrollo', 1, 90),

-- Módulos específicos de 2º DAM (además de EIE que es común)
('AD', 'Acceso a Datos', 2, 140),
('DI', 'Desarrollo de Interfaces', 2, 120),
('PMDM', 'Programación Multimedia y Dispositivos Móviles', 2, 120),
('PSP', 'Programación de Servicios y Procesos', 2, 80),
('SGE', 'Sistemas de Gestión Empresarial', 2, 100),
('HLC-DAM', 'Horas de Libre Configuración (DAM)', 2, 60),
('PDAM', 'Proyecto de Desarrollo de Aplicaciones Multiplataforma', 2, 40),
('FCT-DAM', 'Formación en Centros de Trabajo (DAM)', 2, 400);

-- -------------------------------------------------------------
-- Datos iniciales: Matrículas
-- -------------------------------------------------------------
INSERT INTO matricula (alumno_id, modulo_siglas, fecha_matricula, curso_academico) VALUES
-- Alumno 1 (Juan Perez) - 1º ASIR
(1, 'ISO', '2025-09-10', '25-26'),
(1, 'PAR', '2025-09-10', '25-26'),
(1, 'SGBD', '2025-09-10', '25-26'),
(1, 'FH', '2025-09-10', '25-26'),
(1, 'LMSGI', '2025-09-10', '25-26'),
(1, 'FOL', '2025-09-10', '25-26'),

-- Alumno 2 (Maria Gonzalez) - 1º ASIR
(2, 'ISO', '2025-09-11', '25-26'),
(2, 'PAR', '2025-09-11', '25-26'),
(2, 'SGBD', '2025-09-11', '25-26'),
(2, 'FH', '2025-09-11', '25-26'),
(2, 'LMSGI', '2025-09-11', '25-26'),
(2, 'FOL', '2025-09-11', '25-26'),

-- Alumno 3 (Carlos Ramirez) - 1º ASIR
(3, 'ISO', '2025-09-12', '25-26'),
(3, 'PAR', '2025-09-12', '25-26'),
(3, 'SGBD', '2025-09-12', '25-26'),
(3, 'FH', '2025-09-12', '25-26'),

-- Alumno 4 (Ana Lopez) - 2º ASIR
(4, 'ASO', '2025-09-10', '25-26'),
(4, 'SRI', '2025-09-10', '25-26'),
(4, 'IAW', '2025-09-10', '25-26'),
(4, 'SAD', '2025-09-10', '25-26'),
(4, 'EIE', '2025-09-10', '25-26'),
(4, 'HLC-ASIR', '2025-09-10', '25-26'),

-- Alumno 5 (Luis Martinez) - 2º ASIR
(5, 'ASO', '2025-09-13', '25-26'),
(5, 'SRI', '2025-09-13', '25-26'),
(5, 'IAW', '2025-09-13', '25-26'),
(5, 'SAD', '2025-09-13', '25-26'),

-- Alumno 6 (Elena Fernandez) - 1º DAM
(6, 'SI', '2025-09-15', '25-26'),
(6, 'BBDD', '2025-09-15', '25-26'),
(6, 'PROG', '2025-09-15', '25-26'),
(6, 'ED', '2025-09-15', '25-26'),
(6, 'LMSGI', '2025-09-15', '25-26'),
(6, 'FOL', '2025-09-15', '25-26'),

-- Alumno 7 (David Sanz) - 1º DAM
(7, 'SI', '2025-09-15', '25-26'),
(7, 'BBDD', '2025-09-15', '25-26'),
(7, 'PROG', '2025-09-15', '25-26'),
(7, 'ED', '2025-09-15', '25-26'),
(7, 'LMSGI', '2025-09-15', '25-26'),
(7, 'FOL', '2025-09-15', '25-26'),

-- Alumno 8 (Lucia Moreno) - 1º DAM
(8, 'SI', '2025-09-16', '25-26'),
(8, 'BBDD', '2025-09-16', '25-26'),
(8, 'PROG', '2025-09-16', '25-26'),
(8, 'ED', '2025-09-16', '25-26'),

-- Alumno 9 (Javier Gomez) - 2º DAM
(9, 'AD', '2025-09-16', '25-26'),
(9, 'DI', '2025-09-16', '25-26'),
(9, 'PMDM', '2025-09-16', '25-26'),
(9, 'PSP', '2025-09-16', '25-26'),
(9, 'SGE', '2025-09-16', '25-26'),
(9, 'EIE', '2025-09-16', '25-26'),
(9, 'HLC-DAM', '2025-09-16', '25-26'),

-- Alumno 10 (Paula Jimenez) - 2º DAM
(10, 'AD', '2025-09-17', '25-26'),
(10, 'DI', '2025-09-17', '25-26'),
(10, 'PMDM', '2025-09-17', '25-26'),
(10, 'PSP', '2025-09-17', '25-26'),
(10, 'SGE', '2025-09-17', '25-26'),

-- Alumno 11 (Alejandro Torres) - 2º DAM
(11, 'AD', '2025-09-17', '25-26'),
(11, 'DI', '2025-09-17', '25-26'),
(11, 'PMDM', '2025-09-17', '25-26'),
(11, 'PSP', '2025-09-17', '25-26'),
(11, 'PDAM', '2025-09-17', '25-26'),
(11, 'FCT-DAM', '2025-09-17', '25-26'),

-- Alumno 12 (Sofia Romero) - 2º DAM
(12, 'AD', '2025-09-18', '25-26'),
(12, 'DI', '2025-09-18', '25-26'),
(12, 'PDAM', '2025-09-18', '25-26'),
(12, 'FCT-DAM', '2025-09-18', '25-26'),

-- Alumno 13 (Manuel Rubio) - 2º ASIR
(13, 'ASO', '2025-09-18', '25-26'),
(13, 'SRI', '2025-09-18', '25-26'),
(13, 'PASIR', '2025-09-18', '25-26'),
(13, 'FCT-ASIR', '2025-09-18', '25-26'),

-- Alumno 14 (Carmen Alvarez) - 1º DAM
(14, 'PROG', '2025-09-19', '25-26'),
(14, 'BBDD', '2025-09-19', '25-26'),
(14, 'ED', '2025-09-19', '25-26'),

-- Alumno 15 (Pablo Ortiz) - 1º ASIR
(15, 'ISO', '2025-09-19', '25-26'),
(15, 'PAR', '2025-09-19', '25-26'),
(15, 'SGBD', '2025-09-19', '25-26');
