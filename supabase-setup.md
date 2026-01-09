# Supabase Integration Setup Guide

## Step 1: Create Supabase Project

1. Go to [Supabase](https://supabase.com) and create a new project
2. Choose a project name and database password
3. Wait for the project to be created

## Step 2: Get Connection Details

1. In your Supabase dashboard, go to **Settings** → **Database**
2. Copy the following details:
   - **Host**: Found in "Connection string" section
   - **Database name**: Usually `postgres`
   - **Username**: Usually `postgres`
   - **Password**: The password you set during project creation
   - **Port**: Usually `5432`

## Step 3: Update Application Properties

Replace the placeholders in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://[YOUR_HOST]:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=[YOUR_PASSWORD]
```

Example:
```properties
spring.datasource.url=jdbc:postgresql://db.abcdefghijklmnop.supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=your_secure_password
```

## Step 4: Environment Variables (Recommended)

For security, use environment variables instead of hardcoding credentials:

```properties
spring.datasource.url=${SUPABASE_URL}
spring.datasource.username=${SUPABASE_USERNAME}
spring.datasource.password=${SUPABASE_PASSWORD}
```

Set these environment variables:
- `SUPABASE_URL=jdbc:postgresql://your-host:5432/postgres`
- `SUPABASE_USERNAME=postgres`
- `SUPABASE_PASSWORD=your_password`

## Step 5: Enable Row Level Security (Optional)

In Supabase SQL Editor, you can enable RLS for additional security:

```sql
-- Enable RLS on tables (run after tables are created)
ALTER TABLE users ENABLE ROW LEVEL SECURITY;
ALTER TABLE students ENABLE ROW LEVEL SECURITY;
-- Add policies as needed
```

## Step 6: Test Connection

Run your Spring Boot application. Hibernate will automatically create the tables based on your JPA entities.

## Troubleshooting

- **Connection timeout**: Check if your IP is allowed in Supabase network restrictions
- **SSL errors**: Supabase requires SSL connections by default
- **Authentication failed**: Verify username and password are correct