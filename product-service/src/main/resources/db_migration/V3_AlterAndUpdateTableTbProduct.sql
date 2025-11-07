UPDATE tb_product 
SET image_url = 'https://example.com/images/SedanLuxo2025.jpg' 
WHERE description = 'Sedan Luxo 2025';

UPDATE tb_product 
SET image_url = 'https://example.com/images/SUVCompacto2024.jpg' 
WHERE description = 'SUV Compacto 2024';

UPDATE tb_product 
SET image_url = 'https://example.com/images/Esportivo2025.jpg' 
WHERE description = 'Esportivo 2025';

UPDATE tb_product 
SET image_url = 'https://example.com/images/Pickup2024.jpg' 
WHERE description = 'Pickup 2024';

SELECT description, image_url FROM tb_product;
