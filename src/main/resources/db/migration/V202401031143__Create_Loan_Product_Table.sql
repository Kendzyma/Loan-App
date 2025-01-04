CREATE TABLE IF NOT EXISTS loan_products (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             name VARCHAR(255) NOT NULL,
    interest_rate DOUBLE NOT NULL,
    tenure_months INT NOT NULL,
    status VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );
