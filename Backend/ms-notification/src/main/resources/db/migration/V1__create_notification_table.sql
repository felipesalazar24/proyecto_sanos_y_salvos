-- 🚀 Crear tabla de notificaciones
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    recipient_email VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 📨 Inyectar notificaciones predeterminadas para la demo
INSERT INTO notifications (recipient_email, subject, body, sent_at) VALUES 
('test@duocuc.cl', '¡Bienvenido a Sanos y Salvos!', 'Hola Felipe, tu cuenta ha sido creada con éxito en la plataforma.', CURRENT_TIMESTAMP - INTERVAL '2 hours'),
('test@duocuc.cl', 'Alerta: Mascota Encontrada', 'Se ha reportado una mascota con características similares a la tuya en Peñalolén.', CURRENT_TIMESTAMP - INTERVAL '1 hour'),
('test@duocuc.cl', 'Verificación de Sistema', 'Petición de prueba del microservicio de notificaciones ejecutada correctamente.', CURRENT_TIMESTAMP);