CREATE TABLE IF NOT EXISTS roles(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_role VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS barbershop_user(
    id CHAR(36) PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    user_password VARCHAR(255),
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    auth_provider ENUM('EMAIL', 'GOOGLE') DEFAULT 'EMAIL',
    loyalty_points INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    role_id INT NOT NULL,
    CONSTRAINT fk_bu_role FOREIGN KEY (role_id) REFERENCES roles(id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS password_reset_token(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id CHAR(36) NOT NULL,
    token VARCHAR(100) NOT NULL,
    expires_at DATETIME NOT NULL,
    CONSTRAINT fk_prt_user FOREIGN KEY (user_id) REFERENCES barbershop_user(id) ON DELETE CASCADE
);

-- CONFIGURAÇÃO DA LOJA E HORÁRIOS
CREATE TABLE IF NOT EXISTS store_settings(
    id INT PRIMARY KEY AUTO_INCREMENT,
    store_name VARCHAR(100) NOT NULL,
    address VARCHAR(150),
    phone VARCHAR(20),
    instagram_handle VARCHAR(50),
    show_products_publicly TINYINT(1) DEFAULT 1
);

CREATE TABLE IF NOT EXISTS business_hours(
    id INT PRIMARY KEY AUTO_INCREMENT,
    settings_id INT NOT NULL,
    day_of_week TINYINT NOT NULL,
    is_open TINYINT(1) DEFAULT 1,
    open_time TIME NOT NULL ,
    close_time TIME NOT NULL,
    break_start TIME,
    break_end TIME,
    mode ENUM('APPOINTMENT', 'WALK_IN') DEFAULT 'APPOINTMENT',
    CONSTRAINT fk_bh_settings FOREIGN KEY (settings_id) REFERENCES store_settings(id) ON DELETE CASCADE
);

-- 3. CATÁLOGO (CATEGORIA, PRODUTO, SERVIÇO)
CREATE TABLE IF NOT EXISTS category(
    id CHAR(36) PRIMARY KEY,
    c_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS product(
    id CHAR(36) PRIMARY KEY,
    p_name VARCHAR(50) NOT NULL UNIQUE,
    category_id CHAR(36) NOT NULL ,
    quantity INT DEFAULT 0,
    min_stock INT DEFAULT 5,
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(255),
    is_visible TINYINT(1) DEFAULT 1,
    is_active TINYINT(1) DEFAULT 0,
    CONSTRAINT fk_category_product FOREIGN KEY (category_id) REFERENCES category(id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS service(
    id CHAR(36) PRIMARY KEY,
    s_name VARCHAR(50) NOT NULL UNIQUE,
    s_type ENUM('HAIR','BEARD', 'EYEBROW', 'OTHERS') NOT NULL,
    duration INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(255),
    is_visible TINYINT(1) DEFAULT 1,
    is_active TINYINT(1) DEFAULT 0
);

DELIMITER $$

DROP TRIGGER IF EXISTS trg_create_default_business_hours$$

CREATE TRIGGER trg_create_default_business_hours
    AFTER INSERT ON store_settings
    FOR EACH ROW
BEGIN
    INSERT INTO business_hours (
        settings_id,
        day_of_week,
        is_open,
        open_time,
        close_time,
        mode
    ) VALUES
          -- 1: Segunda-feira
          (NEW.id, 1, 1, '07:00:00', '18:00:00', 'APPOINTMENT'),
          -- 2: Terça-feira
          (NEW.id, 2, 1, '07:00:00', '18:00:00', 'APPOINTMENT'),
          -- 3: Quarta-feira
          (NEW.id, 3, 1, '07:00:00', '18:00:00', 'APPOINTMENT'),
          -- 4: Quinta-feira
          (NEW.id, 4, 1, '07:00:00', '18:00:00', 'APPOINTMENT'),
          -- 5: Sexta-feira
          (NEW.id, 5, 1, '07:00:00', '18:00:00', 'APPOINTMENT'),
          -- 6: Sábado
          (NEW.id, 6, 1, '07:00:00', '18:00:00', 'APPOINTMENT'),
          -- 7: Domingo
          (NEW.id, 7, 1, '07:00:00', '12:00:00', 'APPOINTMENT');

END$$

DELIMITER ;