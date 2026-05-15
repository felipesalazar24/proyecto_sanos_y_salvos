# Backend For Frontend (BFF) - Web Client

## 1. Alcance y Descripción
El **ms-bff** actúa como un orquestador centralizado bajo el patrón **Backend for Frontend**[cite: 192], diseñado para optimizar la interacción del cliente web con el ecosistema de microservicios. Su propósito es la agregación de datos, simplificación de la interfaz y cumplimiento de la seguridad perimetral[cite: 158].

## 2. Patrones de Diseño y Arquitectura
* **Arquitectura de Capas:** Organiza la lógica en niveles claros de responsabilidad: Controller, Service y Client[cite: 283].
* **Patrón Singleton:** Garantiza una única instancia de los componentes mediante el contenedor de inversión de control[cite: 176, 444].
* **Data Transfer Object (DTO):** Desacopla los modelos internos de la representación externa de la API[cite: 35].

## 3. Stack Tecnológico
- **Lenguaje:** Java 17 / Spring Boot 3[cite: 7].
- **Puerto de Servicio:** 8084.
- **Comunicación:** RestTemplate para peticiones síncronas entre servicios[cite: 180].

## 4. Endpoints (Matriz de Orquestación)
| Método | Endpoint | Acción | Servicio Destino |
| :--- | :--- | :--- | :--- |
| POST | `/api/v1/bff/web/login` | Autenticación | `ms-auth` |
| GET | `/api/v1/bff/web/users` | Listado de usuarios | `ms-usuarios` |
| GET | `/api/v1/bff/web/mascotas` | Reporte de mascotas | `ms-mascotas` |

## 5. Beneficios Aplicados
- **Mantenimiento Simplificado:** Al aislar funcionalidades, el impacto de un cambio es predecible y controlado[cite: 144].
- **Escalabilidad:** Estructuras que permiten adaptar la aplicación a nuevos requerimientos con menor esfuerzo[cite: 166].

