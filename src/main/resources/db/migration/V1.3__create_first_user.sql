DO $$
DECLARE
    admin_nome TEXT := 'User 1';
    admin_email TEXT := 'user1@example.com';
    admin_password TEXT := '$2a$10$K2mSJV2BCWuX6yAOcaFawuUEKvXiYsPU9fwBmwEAOrNIQZIMQJWWa';
BEGIN
    INSERT INTO usuario (nome, email, senha, cargo, first_access)
    VALUES (admin_nome, admin_email, admin_password, 'ADMINISTRADOR', FALSE);
END $$;