# Microservicio de Autenticación (ms-auth)

## 1. Alcance y Descripción
Servicio crítico encargado de la validación de credenciales y seguridad perimetral. Actúa como el primer filtro de confianza para el acceso al sistema.

## 2. Patrones de Diseño y Arquitectura
* **Patrón Singleton:** Garantiza una única instancia de los componentes de seguridad, optimizando la memoria y evitando bloqueos de recursos.
* **Arquitectura MVC:** Estructura el flujo de autenticación separando la lógica de validación de la lógica de respuesta.
* **Estrategia de Branching:** Se aplica un desarrollo ordenado mediante ramas independientes para asegurar la estabilidad en cambios críticos.

## 3. Stack Tecnológico
- **Lenguaje:** Java 25 / Spring Boot 4.0.6.
- **Puerto de Servicio:** 8083.
- **Interdependencia:** Conexión interna con `ms-usuarios`.

## 4. Entidades Gestionadas
- **Sesión/Auth:** Gestión de tokens y validación de estados de seguridad de las cuentas de usuario.

## 5. Beneficios Aplicados
- **Robustez:** La aplicación de patrones asegura un rendimiento estable incluso en escenarios de alta demanda.
- **Seguridad en la Integración:** Estandariza el proceso de validación minimizando conflictos y riesgos.