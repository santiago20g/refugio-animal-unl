-- Creación del empleado/administrador por defecto
INSERT INTO employee (id, username, password, employee_id, position) VALUES (1, 'santiago', '12345', 'EMP-001', 'Admin');
INSERT INTO employee (id, username, password, employee_id, position) VALUES (2, 'admin', 'admin', 'EMP-002', 'Manager');

-- Mascotas de prueba con los estados (status) y especies (species) correctos en inglés
INSERT INTO animal (id, name, species, breed, age, status) VALUES (1, 'Firulais', 'DOG', 'Mutt', 2, 'AVAILABLE');
INSERT INTO animal (id, name, species, breed, age, status) VALUES (2, 'Luna', 'CAT', 'Siamese', 1, 'AVAILABLE');
INSERT INTO animal (id, name, species, breed, age, status) VALUES (3, 'Rocky', 'DOG', 'German Shepherd', 4, 'QUARANTINE'); (id, name, species, breed, age, status) VALUES (3, 'Rocky', 'PERRO', 'Pastor Alemán', 4, 'EN_TRATAMIENTO');