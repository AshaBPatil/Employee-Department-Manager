-- departments table
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);

-- employees table
CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

-- join table: employee_departments (many-to-many)
CREATE TABLE employee_departments (
    employee_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    PRIMARY KEY (employee_id, department_id),
    CONSTRAINT fk_emp FOREIGN KEY (employee_id) REFERENCES employees(id),
    CONSTRAINT fk_dept FOREIGN KEY (department_id) REFERENCES departments(id)
);
