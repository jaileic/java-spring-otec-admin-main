# ABP #6 — Entregable OTEC Primavera 🌸

Aplicación web desarrollada con **Spring Boot** para la gestión académica y administrativa de una **OTEC**. El sistema permite administrar cursos, relatores, habilitaciones, estudiantes, prácticas y evaluaciones, incorporando autenticación y reglas de negocio orientadas al contexto **SENCE / REUF**.

---

## ✨ Funcionalidades principales

- 📚 **Gestión de cursos**: crear, editar, listar, activar, desactivar y archivar cursos.
- 👨‍🏫 **Administración de relatores**: registro y validación de relatores con sus habilitaciones.
- ✅ **Control de habilitaciones SENCE/REUF**: validación para asignaciones según especialidad vigente.
- 🎓 **Módulo de estudiantes**: registro, edición y seguimiento de participantes.
- 📝 **Prácticas y evaluaciones**: seguimiento del avance académico de cada estudiante.
- 🔐 **Autenticación y seguridad**: acceso protegido con `Spring Security 6`.
- 🖥️ **Interfaz web server-side**: vistas renderizadas con `Thymeleaf` y estilo con `Bootstrap 5`.

---

## 🧱 Stack tecnológico

| Tecnología | Uso en el proyecto |
|---|---|
| `Java 21` | Lenguaje principal |
| `Spring Boot 3.2.3` | Framework base |
| `Spring MVC` | Controladores y flujo web |
| `Spring Data JPA` | Persistencia de datos |
| `Spring Security 6` | Login y protección de rutas |
| `Thymeleaf` | Renderizado de vistas |
| `MariaDB` | Base de datos principal |
| `H2` | Base de datos para pruebas |
| `Maven` | Build y dependencias |

---

## 🏗️ Arquitectura

El proyecto sigue una estructura por capas:

1. **Controladores**: reciben las solicitudes HTTP.
2. **Servicios**: contienen la lógica de negocio.
3. **Repositorios**: acceso a datos mediante `JpaRepository`.
4. **Modelo / Entidades**: representan las tablas del sistema.
5. **DTOs**: desacoplan la capa web de la persistencia.

Estructura base:

```text
src/main/java/cl/talento/otec/admin/
├── config/
├── controlador/
├── dto/
├── modelo/
├── repositorio/
└── servicio/
```

---

## 🚀 Ejecución local

### 1) Requisitos previos

- `Java 21`
- `Maven` o uso del wrapper `mvnw`
- `MariaDB` en ejecución

### 2) Crear la base de datos

Ejecuta el script:

```text
database/init_otec.sql
```

> Este script crea el esquema `otec_admin_db`, genera las tablas base y carga datos de ejemplo para pruebas locales.

### 3) Configurar credenciales locales

Por seguridad, el archivo real `application.properties` **no se publica** en Git.

1. Ve a `src/main/resources/`
2. Copia `application.properties.ejemplo`
3. Renómbralo a `application.properties`
4. Completa tus credenciales locales de MariaDB

Ejemplo:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/otec_admin_db
spring.datasource.username=TU_USUARIO_MARIADB
spring.datasource.password=TU_PASSWORD_MARIADB
spring.jpa.hibernate.ddl-auto=validate
```

### 4) Levantar la aplicación

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Luego abre en el navegador:

```text
http://localhost:8080/cursos
```

---

## 🧪 Pruebas

Para ejecutar los tests del proyecto:

```powershell
.\mvnw.cmd test
```

---

## 🔒 Consideraciones de seguridad

- `src/main/resources/application.properties` está excluido del repositorio.
- También se ignoran carpetas y archivos locales como `target/`, `.vscode/`, `.env*`, llaves y certificados.
- El repositorio se comparte sin exponer credenciales personales.

---

## 📌 Estado del proyecto

- desarrollo full stack con Java y Spring Boot,
- organización por capas,
- seguridad básica con autenticación,
- persistencia con base de datos relacional,
- y buenas prácticas de publicación segura en GitHub.

Proyecto publicado por **Javiera Leiva**.
