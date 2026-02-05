-- Добавление продуктов
INSERT INTO products (name, type, code, description) VALUES
('Яблоки Гала', 'APPLE', 'APPLE_GALA', 'Сладкие яблоки сорта Гала'),
('Яблоки Голден', 'APPLE', 'APPLE_GOLDEN', 'Кисло-сладкие яблоки сорта Голден'),
('Груши Конференция', 'PEAR', 'PEAR_CONFERENCE', 'Сладкие груши сорта Конференция'),
('Груши Аббат', 'PEAR', 'PEAR_ABBOT', 'Ароматные груши сорта Аббат');

-- Добавление поставщиков
INSERT INTO suppliers (name, inn, address, contact_person, phone, email, active, created_at) VALUES
('ООО Фруктовый рай', '1234567890', 'г. Москва, ул. Садовая, д. 1', 'Иванов Иван', '+7 (999) 123-45-67', 'info@fruit-paradise.ru', true, NOW()),
('АО Яблочный край', '0987654321', 'г. Краснодар, ул. Фруктовая, д. 15', 'Петрова Мария', '+7 (988) 765-43-21', 'sales@apple-land.ru', true, NOW()),
('ИП Сидоров Сады', '1122334455', 'г. Воронеж, ул. Садовая, д. 42', 'Сидоров Алексей', '+7 (977) 555-44-33', 'sidorov@sady.ru', true, NOW());

-- Добавление цен поставщиков (актуальные цены)
INSERT INTO supplier_prices (supplier_id, product_id, price, valid_from, valid_to, created_at) VALUES
(1, 1, 85.50, CURRENT_DATE, NULL, NOW()),
(1, 2, 92.00, CURRENT_DATE, NULL, NOW()),
(1, 3, 120.00, CURRENT_DATE, NULL, NOW()),
(1, 4, 135.00, CURRENT_DATE, NULL, NOW()),
(2, 1, 82.00, CURRENT_DATE, NULL, NOW()),
(2, 2, 88.50, CURRENT_DATE, NULL, NOW()),
(2, 3, 118.00, CURRENT_DATE, NULL, NOW()),
(2, 4, 130.00, CURRENT_DATE, NULL, NOW()),
(3, 1, 87.00, CURRENT_DATE, NULL, NOW()),
(3, 2, 94.00, CURRENT_DATE, NULL, NOW()),
(3, 3, 125.00, CURRENT_DATE, NULL, NOW()),
(3, 4, 140.00, CURRENT_DATE, NULL, NOW());
