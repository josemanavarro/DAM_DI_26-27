CREATE DATABASE IF NOT EXISTS instituto;

USE instituto;

CREATE TABLE IF NOT EXISTS alumnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,    
    edad INT NOT NULL
);

INSERT INTO alumnos (nombre, apellidos, edad) VALUES
('Juan', 'Perez', 20),
('Maria', 'Gonzalez', 22),
('Carlos', 'Ramirez', 19),
('Ana', 'Lopez', 21),
('Luis', 'Martinez', 23);
