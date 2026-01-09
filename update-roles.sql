-- Run this SQL in Supabase SQL Editor to update the role constraint
-- This is needed because the original constraint only allowed ADMIN and STUDENT

-- Drop the existing check constraint
ALTER TABLE users DROP CONSTRAINT IF EXISTS users_role_check;

-- Add the new check constraint with all four roles
ALTER TABLE users ADD CONSTRAINT users_role_check 
CHECK (role IN ('ADMIN', 'HOD', 'FACULTY', 'STUDENT'));

-- Verify the constraint was updated
SELECT conname, pg_get_constraintdef(oid) 
FROM pg_constraint 
WHERE conrelid = 'users'::regclass AND contype = 'c';
