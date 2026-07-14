flowchart LR
    Client[Cliente o navegador] -->|Entra al sistema| Gateway[API Gateway]

    Gateway -->|Ruta tráfico a la capa de aplicación| BFF[bff]
    Gateway -->|Enruta auth específica| AUTH[ms-auth]
    Gateway -->|Enruta usuarios| USER[ms-usuario]
    Gateway -->|Enruta mascotas| PET[ms-mascota]

    Gateway -->|Centraliza acceso y control| Security[Seguridad / Routing]
    Security -->|Aplica políticas de acceso| Gateway