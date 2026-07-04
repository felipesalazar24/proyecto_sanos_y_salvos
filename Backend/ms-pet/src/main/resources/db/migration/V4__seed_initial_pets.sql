INSERT INTO pets (
    name, age_category, type_id, user_id, 
    last_seen_location, last_seen_date, color, description, status
) VALUES (
    'Firulais', 
    'Adulto', 
    1, -- Pet type ID
    1, -- User ID
    'Cerca de Mall Plaza Tobalaba', 
    CURRENT_TIMESTAMP, 
    'Café con manchas negras', 
    'Anda con un collar rojo, es súper dócil pero asustadizo.', 
    'PERDIDO'
);