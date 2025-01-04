CREATE TABLE IF NOT EXISTS transactions (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            loan_id BIGINT NOT NULL,
                                            user_id BIGINT NOT NULL,
                                            amount DOUBLE NOT NULL,
                                            type VARCHAR(255) NOT NULL,
    FOREIGN KEY (loan_id) REFERENCES loans(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );