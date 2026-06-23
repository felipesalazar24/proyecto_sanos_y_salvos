INSERT INTO pet_types (name_type, breed) VALUES 
('Perro', 'Quiltro / Mestizo'),
('Perro', 'Pastor Alemán'),
('Perro', 'Poodle'),
('Gato', 'Común Europeo'),
('Gato', 'Siamés'),
('Gato', 'Persa')
ON CONFLICT DO NOTHING;