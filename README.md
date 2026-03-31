# Sistema de Gestión de Cursos - OTEC Admin

Este proyecto es un mantenedor CRUD funcional desarrollado en **Spring Boot**, diseñado para la administración de la malla de cursos y **secciones** de un Organismo Técnico de Capacitación (OTEC), como los dictados para Talento Digital. Implementa estándares de la industria como arquitectura de capas, el uso estricto del patrón **DTO** y prácticas modernas de **Spring Security 6**.

## 🚀 Características Principales

* **Gestión de Cursos y Cohortes:** Capacidad para listar, crear, editar y aplicar borrado lógico a cursos y secciones. Soporta planificación temprana permitiendo crear cursos sin un relator asignado. Reemplaza el concepto de "canal" por un `codigoInterno` para la trazabilidad logística.
* **Cumplimiento Normativo (SENCE - REUF):** Validación estricta en el backend que impide asignar un relator a un curso si no cuenta con la habilitación SENCE vigente para ese código de especialidad.
* **Ciclo de Vida Dual:** Separación de estados para reflejar la realidad del negocio: Estado Académico (`activo`/`inactivo`) y Estado de Visibilidad (`archivado`/`no archivado`).
* **Seguridad Base (Prueba de Concepto):** Protección de rutas (`/cursos/**`, `/relatores/**`) mediante Spring Security 6 utilizando autenticación en memoria y encriptación BCrypt, sin uso de herencia obsoleta (`WebSecurityConfigurerAdapter`).
* **Separación de Responsabilidades:** Código estructurado para diferenciar claramente la lógica de acceso a datos, las reglas de negocio y la presentación web.

## 🏗️ Arquitectura del Proyecto

El proyecto sigue un riguroso patrón de **3 Capas** para asegurar la escalabilidad:

1. **Modelo (Entity):** Representación de las tablas en MariaDB mediante JPA (Cursos, Relatores, Habilitaciones).
2. **Repositorio:** Interfaz que extiende de `JpaRepository` para operaciones de persistencia automatizadas.
3. **Servicio:** Capa de lógica de negocio donde se realiza el mapeo bidireccional de Entidades a DTOs y se validan las reglas del OTEC.
4. **Controlador:** Maneja las peticiones HTTP y devuelve las vistas web renderizadas.
5. **DTO (Data Transfer Object):** Objetos de transferencia para aislar la base de datos y exponer solo los datos necesarios a la vista.
6. **Configuración de Seguridad:** Uso de `SecurityFilterChain` para la gestión de filtros HTTP y autorización basada en componentes.

## 🛠️ Tecnologías Utilizadas

* **Java 17+**
* **Spring Boot 3.x** (Web, Data JPA, Security)
* **Spring Security 6**
* **Thymeleaf** (Motor de plantillas renderizado en servidor)
* **MariaDB** (Base de datos relacional)
* **Maven** (Gestión de dependencias)
* **Bootstrap 5** (Diseño y componentes UI)

## 🗄️ Configuración y Despliegue Local

Para levantar este proyecto en tu entorno local, sigue estos pasos:

### 1. Inicialización de la Base de Datos
El script maestro con la estructura final y datos de prueba reales está versionado en el repositorio.
* Ejecuta el archivo `database/init_otec.sql` en tu gestor de base de datos (DBeaver, HeidiSQL, etc.).
* Este script destruirá/creará el esquema `otec_admin_db`, configurará las tablas (`relatores`, `cursos`, `habilitaciones`) y poblará el catálogo base para validar las reglas de asignación SENCE.

### 2. Configuración de Credenciales (application.properties)
Por motivos de seguridad, el archivo con las credenciales reales de conexión a la base de datos **no está versionado** en Git. 
* Dirígete a la ruta `src/main/resources/`.
* Encontrarás un archivo de plantilla llamado `application.properties.ejemplo`. Este archivo contiene el formato exacto de las propiedades que necesita el proyecto.
* Haz una copia de ese archivo en la misma carpeta y renómbrala a `application.properties`.
* Abre tu nuevo `application.properties` e ingresa tu `username` y `password` reales de MariaDB.

### 3. Ejecución
* Ejecuta la clase principal de Spring Boot.
* Asegúrate de que la propiedad `spring.jpa.hibernate.ddl-auto` esté en `none` o `validate` para evitar que Hibernate modifique la estructura creada por el script SQL.
* Accede al sistema ingresando a `http://localhost:8080/cursos`.