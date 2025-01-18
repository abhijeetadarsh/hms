# Hospital Management System (HMS) Deployment Guide

## Prerequisites
- Docker Engine 24.0+
- Docker Compose v2.0+
- Git
- Basic understanding of Docker and Spring Boot

## Step 1: Environment Setup

### 1.1 Clone the Repository
```bash
git clone https://github.com/abhijeetadarsh/hms
cd hms
```

### 1.2 Configure Environment Variables
Docker Compose resolves environment variables in the following order:
1. `.env` file in the same directory as the Compose file
2. Environment variables set in the shell/system
3. Default values specified in the Compose file (after `:-`)

To set up environment variables:
1. Create a `.env` file from the template:
   ```bash
   cp .env.example .env
   ```
2. Edit the `.env` file with secure values.

To generate a secure JWT secret:
```bash
# Set the secret before running docker-compose
export JWT_SECRET=$(openssl rand -base64 32)
```

## Step 2: Deployment Commands

### 2.1 First-Time Deployment
```bash
# Build and start all services
docker-compose up -d --build

# Check services status
docker-compose ps

# View logs
docker-compose logs -f
```

### 2.2 Update Application (No Database Changes)
```bash
# Rebuild and restart only the application
docker-compose up -d --build app
```

### 2.3 Complete Reset (Including Database)
```bash
# Stop all services and remove volumes
docker-compose down -v

# Rebuild and start
docker-compose up -d --build
```

### 2.4 Database Backup and Restore
#### Backup Database
```bash
# Create a database backup
docker exec hms_v2-db-1 mysqldump -u root -p${MYSQL_ROOT_PASSWORD} hms > backup.sql
```

#### Restore Database
```bash
# Restore a database backup
docker exec -i hms_v2-db-1 mysql -u root -p${MYSQL_ROOT_PASSWORD} hms < backup.sql
```

## Step 3: Monitoring and Maintenance

### 3.1 View Logs
#### All Services
```bash
docker-compose logs -f
```

#### Specific Services
```bash
# Application
docker-compose logs -f app

# Database
docker-compose logs -f db
```

### 3.2 Check Resource Usage
```bash
docker stats
```

### 3.3 Container Management
#### Manage All Services
```bash
# Stop all services
docker-compose stop

# Start all services
docker-compose start
```

#### Restart Specific Service
```bash
# Restart the application service
docker-compose restart app
```

