-- Creación del empleado/administrador por defecto
INSERT INTO employee (id, username, password, employee_id, position) VALUES (1, 'santiago', '12345', 'EMP-001', 'Admin');
INSERT INTO employee (id, username, password, employee_id, position) VALUES (2, 'admin', 'admin', 'EMP-002', 'Manager');

-- Mascotas de prueba con los estados (status) y especies (species) correctos en inglés
INSERT INTO animal (id, name, species, breed, age, status, animal_code, sex) VALUES (1, 'Firulais', 'DOG', 'Mutt', 2, 'AVAILABLE', 'AN-001', 'M');
INSERT INTO animal (id, name, species, breed, age, status, animal_code, sex) VALUES (2, 'Luna', 'CAT', 'Siamese', 1, 'AVAILABLE', 'AN-002', 'F');
INSERT INTO animal (id, name, species, breed, age, status, animal_code, sex) VALUES (3, 'Rocky', 'DOG', 'German Shepherd', 4, 'QUARANTINE', 'AN-003', 'M');

-- Sincroniza las secuencias de identidad de Postgres con los IDs insertados
-- manualmente arriba. Sin esto, Postgres no sabe que los IDs 1-3 (animal) y
-- 1-2 (employee) ya están ocupados, y al generar el siguiente ID automático
-- para un registro nuevo choca con estos datos de prueba
-- (duplicate key value violates unique constraint "animal_pkey").
SELECT setval(pg_get_serial_sequence('animal', 'id'), (SELECT MAX(id) FROM animal));
SELECT setval(pg_get_serial_sequence('employee', 'id'), (SELECT MAX(id) FROM employee));
