CREATE TABLE IF NOT EXISTS loans (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     user_id BIGINT NOT NULL,
                                     product_id BIGINT NOT NULL,
                                     amount DOUBLE NOT NULL,
                                        outstanding_balance DOUBLE NOT NULL,
                                     interest_rate DOUBLE NOT NULL,
                                     status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    approval_date TIMESTAMP DEFAULT NULL,
    repayment_date TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES loan_products(id)
    );