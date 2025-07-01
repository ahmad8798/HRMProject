-- Create roles table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    display_name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Insert default roles
INSERT INTO roles (name, display_name, description) VALUES 
('HR_ADMIN', 'HR Administrator', 'Full system access for HR administrators'),
('USER', 'Regular User', 'Basic access for regular employees'),
('ADMIN', 'Administrator (Legacy)', 'Legacy admin role (maps to HR_ADMIN)');

-- Add role_id column to users table (nullable during migration)
ALTER TABLE users ADD COLUMN role_id INTEGER;

-- Add foreign key constraint
ALTER TABLE users ADD CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id);

-- Update existing users based on their string role
UPDATE users SET role_id = (SELECT id FROM roles WHERE name = users.role);

-- Note: Keep the old role column during transition
-- ALTER TABLE users DROP COLUMN role; -- Run this later when migration is complete
