# Microservicio de Usuarios (ms-usuarios)

## 1. Alcance y Descripción
Componente encargado de la gobernanza de identidad y persistencia de perfiles de usuario. Provee las capacidades de CRUD necesarias para el funcionamiento del ecosistema.

## 2. Patrones de Diseño y Arquitectura
* **Repository Pattern:** Capa de acceso a datos que **abstrae la comunicación con la base de datos**.
* **Arquitectura MVC:** Separación estricta entre la exposición de recursos (Controller), lógica (Service) y persistencia (Repository).
* **Inyección de Dependencias:** Facilita la modularización y la adopción de buenas prácticas en el equipo.

## 3. Stack Tecnológico
- **Lenguaje:** Java 25 / Spring Boot 4.0.6.
- **Puerto de Servicio:** 8081.
- **Base de Datos:** PostgreSQL (Contenedor Local).

## 4. Entidades Gestionadas
- **Usuario:** Registro de perfiles con atributos de identidad, contacto, ubicación geográfica y roles de sistema.

## 5. Beneficios Aplicados
- **Escalabilidad:** Permite el crecimiento ordenado y seguro del sistema de identidad.
- **Mantenimiento Simplificado:** Facilita ubicar y corregir errores al aislar la lógica en componentes especializados.
