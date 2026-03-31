-- 1. Creamos la base de datos
CREATE DATABASE IF NOT EXISTS otec_admin_db;
USE otec_admin_db;

-- 2. Limpiamos tablas antiguas (orden inverso a las dependencias)
DROP TABLE IF EXISTS evaluaciones;
DROP TABLE IF EXISTS practicas;
DROP TABLE IF EXISTS estudiantes;
DROP TABLE IF EXISTS habilitaciones;
DROP TABLE IF EXISTS cursos;
DROP TABLE IF EXISTS relatores;
DROP TABLE IF EXISTS usuarios;

-- 3. Tabla Relatores
CREATE TABLE relatores (
    id_relator INT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(12) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    profesion_titulo VARCHAR(150),
    activo BOOLEAN DEFAULT TRUE
);

-- 4. Tabla Cursos
CREATE TABLE cursos (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50),
    nombre VARCHAR(255),
    codigo_interno VARCHAR(50),
    duracion_horas INT,
    codigo_sence VARCHAR(50),
    categoria VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    archivado BOOLEAN DEFAULT FALSE,
    id_relator INT NULL,
    FOREIGN KEY (id_relator) REFERENCES relatores(id_relator)
);

-- 5. Tabla Habilitaciones (SENCE - REUF)
CREATE TABLE habilitaciones (
    id_habilitacion INT AUTO_INCREMENT PRIMARY KEY,
    id_relator INT NOT NULL,
    area_curso VARCHAR(100),
    codigo_especialidad_sence VARCHAR(50) NOT NULL,
    estado_reuf VARCHAR(50) DEFAULT 'Vigente',
    fecha_vencimiento_reuf DATE NOT NULL,
    estado VARCHAR(50),
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_relator) REFERENCES relatores(id_relator) ON DELETE CASCADE
);

-- 6. Tabla Usuarios para inicio de sesión
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- 7. Tabla Estudiantes
CREATE TABLE estudiantes (
    id_estudiante INT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(12) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    programa VARCHAR(150),
    activo BOOLEAN DEFAULT TRUE,
    id_curso INT NULL,
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
);

-- 8. Tabla Prácticas
CREATE TABLE practicas (
    id_practica INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    fecha_entrega DATE,
    estado VARCHAR(50),
    id_estudiante INT NOT NULL,
    id_curso INT NULL,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(id_estudiante) ON DELETE CASCADE,
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
);

-- 9. Tabla Evaluaciones
CREATE TABLE evaluaciones (
    id_evaluacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    tipo VARCHAR(50),
    nota DECIMAL(4,2),
    fecha_evaluacion DATE,
    observacion VARCHAR(500),
    id_estudiante INT NOT NULL,
    id_curso INT NULL,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(id_estudiante) ON DELETE CASCADE,
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
);

-- Nota: los usuarios demo se crean automáticamente al iniciar la aplicación.

-- 10. Insertar Data de Prueba
INSERT INTO relatores (id_relator, rut, nombres, apellidos, email, profesion_titulo, activo) VALUES
(1, '15123456-7', 'Andrea', 'Vergara', 'andrea.vergara@otec.cl', 'Ingeniera Civil en Computación', true),
(2, '16987654-3', 'Felipe', 'Contreras', 'felipe.contreras@otec.cl', 'Analista Programador', true);

INSERT INTO habilitaciones (id_relator, area_curso, codigo_especialidad_sence, estado_reuf, fecha_vencimiento_reuf, estado, activo) VALUES
(1, 'Programación', '12-38-0517-74', 'Vigente', '2026-12-31', 'Aprobado Interno', true),
(1, 'Datos e IA', '12-38-0514-68', 'Vigente', '2026-12-31', 'Aprobado Interno', true),
(2, 'Programación', '12-38-0516-72', 'Vigente', '2026-10-15', 'Aprobado Interno', true),
(2, 'Programación', '12-38-0515-70', 'Vigente', '2026-10-15', 'Aprobado Interno', true),
(2, 'Programación', '12-38-0518-80', 'Vigente', '2026-10-15', 'Aprobado Interno', true);

INSERT INTO cursos (codigo, nombre, codigo_interno, duracion_horas, codigo_sence, categoria, activo, archivado, id_relator) VALUES
('RTD-24-01-05-0043-2', 'DESARROLLO DE APLICACIONES MÓVILES ANDROID TRAINEE V2.0', 'AND-G1', 400, '12-38-0518-80', 'Programación', true, false, NULL),
('RTD-25-01-14-0063-1', 'FUNDAMENTOS DE ANÁLISIS DE DATOS', 'FAD-G1', 120, '12-38-0514-68', 'Datos e IA', true, false, NULL),
('RTD-25-01-01-0001-1', 'DESARROLLO DE APLICACIONES FULLSTACK PYTHON TRAINEE', 'FSPY-G1', 450, '12-38-0517-74', 'Programación', true, false, NULL),
('RTD-25-01-02-0006-1', 'DESARROLLO DE APLICACIONES FULL STACK JAVASCRIPT TRAINEE', 'FSJS-G1', 450, '12-38-0515-70', 'Programación', true, false, NULL),
('RTD-25-01-07-0028-1', 'DESARROLLO DE APLICACIONES FULL STACK JAVA TRAINEE', 'FSJV-G1', 480, '12-38-0516-72', 'Programación', true, false, NULL);

INSERT INTO estudiantes (rut, nombres, apellidos, email, programa, activo, id_curso) VALUES
('20.456.789-1', 'Camila', 'Rojas', 'camila.rojas@primavera.cl', 'Bootcamp Desarrollo Full Stack Java', true, 5),
('21.654.987-2', 'Martín', 'González', 'martin.gonzalez@primavera.cl', 'Bootcamp Desarrollo Web', true, 4);

INSERT INTO practicas (titulo, descripcion, fecha_entrega, estado, id_estudiante, id_curso) VALUES
('Práctica MVC - CRUD de estudiantes', 'Implementar formularios y listado con Spring MVC y Thymeleaf.', '2026-04-10', 'Pendiente', 1, 5),
('Práctica API REST - Cursos', 'Validar operaciones CRUD de cursos y estudiantes desde Postman.', '2026-04-15', 'En progreso', 2, 4);

INSERT INTO evaluaciones (nombre, tipo, nota, fecha_evaluacion, observacion, id_estudiante, id_curso) VALUES
('Evaluación Spring MVC', 'Sumativa', 6.40, '2026-03-28', 'Buen dominio de controladores y vistas.', 1, 5),
('Evaluación APIs RESTful', 'Formativa', 5.90, '2026-03-29', 'Debe reforzar el uso de PUT y DELETE.', 2, 4);
