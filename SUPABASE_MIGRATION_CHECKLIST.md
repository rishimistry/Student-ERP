# Supabase Migration Checklist

## ✅ Completed Changes

1. **Updated Maven Dependencies**
   - ✅ Replaced MySQL connector with PostgreSQL driver in `pom.xml`

2. **Updated Configuration**
   - ✅ Modified `application.properties` with Supabase PostgreSQL settings
   - ✅ Changed Hibernate dialect to PostgreSQL
   - ✅ Created template configuration file `application-supabase.properties`

3. **Documentation**
   - ✅ Created comprehensive setup guide in `supabase-setup.md`
   - ✅ Updated `setup.sql` for PostgreSQL/Supabase

## 🔄 Next Steps (Manual)

### 1. Create Supabase Project
- Go to [supabase.com](https://supabase.com)
- Create new project
- Note down your credentials

### 2. Update Connection Details
Replace placeholders in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://[YOUR_SUPABASE_HOST]:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=[YOUR_SUPABASE_PASSWORD]
```

### 3. Test the Migration
```bash
mvn clean compile
mvn spring-boot:run
```

### 4. Verify Database Tables
- Check Supabase dashboard → Table Editor
- Tables should be auto-created by Hibernate

## 🔧 Environment Variables (Recommended)

For production, use environment variables:
```bash
export SUPABASE_URL="jdbc:postgresql://your-host:5432/postgres"
export SUPABASE_USERNAME="postgres"
export SUPABASE_PASSWORD="your_password"
```

Then update `application.properties`:
```properties
spring.datasource.url=${SUPABASE_URL}
spring.datasource.username=${SUPABASE_USERNAME}
spring.datasource.password=${SUPABASE_PASSWORD}
```

## 🚨 Important Notes

- **Backup Data**: If you have existing MySQL data, export it first
- **SSL Required**: Supabase requires SSL connections (handled automatically)
- **Connection Limits**: Supabase has connection limits based on your plan
- **IP Restrictions**: Check if your IP needs to be whitelisted in Supabase

## 🐛 Troubleshooting

- **Connection refused**: Check Supabase host and port
- **Authentication failed**: Verify username/password
- **SSL errors**: Ensure SSL is enabled (default in Supabase)
- **Table creation issues**: Check Hibernate logs for errors