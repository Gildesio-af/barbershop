-- 1. AGENDAMENTOS (Cabeçalho)
CREATE TABLE IF NOT EXISTS appointment(
    id CHAR(36) PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    appointment_date DATETIME NOT NULL,
    total_amount DECIMAL(10,2),
    total_duration INT,
    app_status ENUM('PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELED') DEFAULT 'PENDING',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_user FOREIGN KEY (user_id) REFERENCES barbershop_user(id)
    );

-- 2. ITENS DO AGENDAMENTO (Para permitir múltiplos serviços: Corte + Barba)
CREATE TABLE IF NOT EXISTS appointment_services(
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id CHAR(36) NOT NULL,
    service_id CHAR(36) NOT NULL,
    price_at_moment DECIMAL(10,2) NOT NULL, -- Preço na hora do agendamento
    CONSTRAINT fk_as_app FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE,
    CONSTRAINT fk_as_srv FOREIGN KEY (service_id) REFERENCES service(id)
    );

-- 3. CONSUMO NO AGENDAMENTO (Produtos comprados na hora do corte)
CREATE TABLE IF NOT EXISTS appointment_products(
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id CHAR(36) NOT NULL,
    product_id CHAR(36) NOT NULL,
    quantity INT DEFAULT 1,
    price_at_moment DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_ap_app FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE,
    CONSTRAINT fk_ap_prod FOREIGN KEY (product_id) REFERENCES product(id)
    );