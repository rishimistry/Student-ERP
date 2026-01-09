# How to Get Supabase Connection Details

## Step 1: Create Supabase Account & Project

1. Go to **[supabase.com](https://supabase.com)**
2. Click **"Start your project"** or **"Sign Up"**
3. Sign up with GitHub, Google, or email
4. Click **"New Project"**
5. Choose your organization (or create one)
6. Fill in project details:
   - **Name**: `student-portal` (or any name you prefer)
   - **Database Password**: Create a strong password (SAVE THIS!)
   - **Region**: Choose closest to your location
7. Click **"Create new project"**
8. Wait 2-3 minutes for project setup

## Step 2: Get Database Connection Details

Once your project is ready:

### Method 1: From Settings → Database
1. In your Supabase dashboard, click **"Settings"** (gear icon in sidebar)
2. Click **"Database"** in the settings menu
3. Scroll down to **"Connection string"** section
4. You'll see: `postgresql://postgres:[YOUR-PASSWORD]@[HOST]:[PORT]/postgres`

### Method 2: From Project Settings
1. Go to **Settings** → **General**
2. Find **"Reference ID"** - this is part of your host URL
3. Your host will be: `db.[REFERENCE-ID].supabase.co`

## Step 3: Extract the Details

From the connection string, extract these values:

```
postgresql://postgres:[PASSWORD]@[HOST]:[PORT]/postgres
```

**Example connection string:**
```
postgresql://postgres:your_password@db.abcdefghijklmnop.supabase.co:5432/postgres
```

**Extract these details:**
- **Host**: `db.abcdefghijklmnop.supabase.co`
- **Port**: `5432` (always 5432 for Supabase)
- **Database**: `postgres` (always postgres for Supabase)
- **Username**: `postgres` (always postgres for Supabase)
- **Password**: The password you set when creating the project

## Step 4: Update Your Application

Replace the placeholders in your `application.properties`:

```properties
# Before (with placeholders)
spring.datasource.url=jdbc:postgresql://[YOUR_SUPABASE_HOST]:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=[YOUR_SUPABASE_PASSWORD]

# After (with actual values)
spring.datasource.url=jdbc:postgresql://db.abcdefghijklmnop.supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=your_actual_password
```

## Step 5: Alternative - Using Direct Copy

Supabase also provides ready-to-use connection strings:

1. Go to **Settings** → **Database**
2. Look for **"Connection pooling"** section
3. Choose **"Session mode"**
4. Copy the **JDBC URL** (if available) or use the PostgreSQL connection string

## Important Notes

- **Save your password**: You set this during project creation and cannot view it again
- **Host format**: Always `db.[project-ref].supabase.co`
- **Port**: Always `5432`
- **Database name**: Always `postgres`
- **Username**: Always `postgres`

## Security Best Practices

Instead of hardcoding credentials, use environment variables:

```properties
spring.datasource.url=${SUPABASE_URL:jdbc:postgresql://localhost:5432/postgres}
spring.datasource.username=${SUPABASE_USERNAME:postgres}
spring.datasource.password=${SUPABASE_PASSWORD:}
```

Set environment variables:
```bash
# Windows
set SUPABASE_URL=jdbc:postgresql://db.your-ref.supabase.co:5432/postgres
set SUPABASE_USERNAME=postgres
set SUPABASE_PASSWORD=your_password

# Linux/Mac
export SUPABASE_URL="jdbc:postgresql://db.your-ref.supabase.co:5432/postgres"
export SUPABASE_USERNAME="postgres"
export SUPABASE_PASSWORD="your_password"
```

## Troubleshooting

**Can't find connection details?**
- Make sure your project is fully created (green status)
- Check Settings → Database section
- Look for "Connection string" or "Connection info"

**Forgot your password?**
- Go to Settings → Database
- Click "Reset database password"
- Set a new password

**Connection refused?**
- Check if your IP needs to be whitelisted
- Go to Settings → Authentication → URL Configuration
- Add your IP to allowed origins if needed