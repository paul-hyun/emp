CREATE USER emp_orange
    WITH
        PASSWORD 'emp_orange'
    SUPERUSER;

DROP DATABASE IF EXISTS emp_orange;
    
CREATE DATABASE emp_orange
    WITH
        OWNER = emp_orange
        ENCODING = 'UTF8'
        TABLESPACE = pg_default
        CONNECTION LIMIT = -1;
