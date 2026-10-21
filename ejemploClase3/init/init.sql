CREATE DATABASE IF NOT EXISTS instituto;

USE instituto;

CREATE TABLE IF NOT EXISTS alumnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,    
    edad INTEGER NOT NULL
);

INSERT INTO alumnos (nombre, apellido, edad) VALUES
('Juan', 'Perez', 20),
('María', 'Gonzalez', 22),
('Carlos', 'Ramirez', 19),
('Ana', 'Lopez', 21),
('Luis', 'Martinez', 23);
