-- liquibase formatted sql

-- changeset akadachigov:1

CREATE INDEX student_name_index ON student (name);

-- changeset akadachigov:2

CREATE INDEX faculty_name_color_index ON faculty (name, color);

