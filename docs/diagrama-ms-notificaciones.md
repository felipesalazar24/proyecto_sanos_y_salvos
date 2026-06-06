```mermaid
graph TD
    classDef ms fill:#bfb,stroke:#333,stroke-width:2px;
    classDef broker fill:#fbb,stroke:#333,stroke-width:2px;
    classDef ext fill:#ddd,stroke:#333,stroke-width:1px;

    %% Origen de Eventos
    Broker[Event Broker]:::broker -.->|Suscripción: Evento Mascota_Perdida| MS_Notif[Servicio Notificaciones <br> ms-notificacion]:::ms
    
    %% Procesamiento del Microservicio
    MS_Notif -->|Consulta Plantilla| DB_Notif[(Log / Plantillas)]
    
    %% Salida a proveedores exteranos
    MS_Notif -->|API Rest External| Email[Proveedor Email <br> SendGrid / Mailchimp]:::ext
    MS_Notif -->|API Rest External| Push[Servicio Push <br> Firebase Cloud Messaging]:::ext
```mermaid