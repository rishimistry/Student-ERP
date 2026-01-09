-- Supabase PostgreSQL Database Setup
-- This file is for reference only - Hibernate will auto-create tables
-- Run these commands in Supabase SQL Editor if you want to create tables manually

-- Note: With spring.jpa.hibernate.ddl-auto=update, 
-- Hibernate will automatically create these tables based on your JPA entities

-- Optional: Create database (usually not needed in Supabase)
-- CREATE DATABASE student_erp;

-- Optional: Enable extensions that might be useful
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- The following tables will be auto-created by Hibernate:
-- - users (User entity)
-- - students (Student entity) 
-- - subjects (Subject entity)
-- - attendance (Attendance entity)
-- - marks (Mark entity)
-- - notes (Note entity)
-- - notices (Notice entity)

-- Optional: Create indexes for better performance (run after tables are created)
-- CREATE INDEX IF NOT EXISTS idx_students_user_id ON students(user_id);
-- CREATE INDEX IF NOT EXISTS idx_attendance_student_id ON attendance(student_id);
-- CREATE INDEX IF NOT EXISTS idx_marks_student_id ON marks(student_id);
-- CREATE INDEX IF NOT EXISTS idx_notices_created_at ON notices(created_at);
