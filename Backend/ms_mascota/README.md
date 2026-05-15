# Microservicio de Mascotas (ms-mascotas)

## 1. Alcance y Descripción
Módulo especializado en la gestión de registros de mascotas y clasificaciones. Este servicio destaca por su arquitectura híbrida con persistencia en la nube.

## 2. Patrones de Diseño y Arquitectura
* **Repository Pattern:** Aplicado para la **abstracción de la fuente de datos**, permitiendo que el servicio sea agnóstico a la infraestructura.
* **Separación de Responsabilidades:** Evita que el acceso a la base de datos esté mezclado con el código de los controladores.
* **Singleton:** Garantiza una gestión optimizada de la conexión a la base de datos administrada.

## 3. Stack Tecnológico
- **Lenguaje:** Java 25 / Spring Boot 4.0.6.
- **Puerto de Servicio:** 8082.
- **Base de Datos:** PostgreSQL (Neon Cloud).

## 4. Entidades Gestionadas
- **Mascota:** Registro de avistamientos, descripción física, estado y ubicación.
- **TipoMascota:** Catálogo maestro de especies y razas.

## 5. Beneficios Aplicados
- **Flexibilidad:** Facilita la integración de nuevas tecnologías de base de datos sin reescribir código de negocio.
- **Cohesión:** Código más legible y organizado mediante el uso de patrones consolidados.

