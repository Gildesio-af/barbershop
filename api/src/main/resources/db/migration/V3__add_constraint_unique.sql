ALTER TABLE appointment_services
    ADD CONSTRAINT unique_appointment_service UNIQUE (appointment_id, service_id);

ALTER TABLE appointment_products
    ADD CONSTRAINT unique_appointment_product UNIQUE (appointment_id, product_id);