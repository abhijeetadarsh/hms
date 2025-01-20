# Sample README.md content for main documentation:

# Hospital Management System (HMS)

## System Overview
The Hospital Management System (HMS) is a comprehensive solution for managing hospital operations, including patient management, appointment scheduling, medical records, and billing.

## Documentation Structure

### 1. Architecture Documentation
- [System Overview](architecture/system-overview.md)
- [Data Flow Diagrams](architecture/DFD.md)
- [Entity Relationship Diagrams](architecture/ERD.md)

### 2. Database Design
- [Database Schema](database/README.md)
- [Schema Evolution](database/schema/migrations/)
- [Database Analysis](database/schemacrawler/)

### 3. API Documentation
- [API Endpoints](api/endpoints.md)
- [Swagger Documentation](api/swagger/)
- [Postman Collection](api/postman/)

## Quick Start Guide

1. **Database Schema Understanding**
    - Review the ER diagram in `docs/images/erd.png`
    - Check detailed schema in `docs/database/schema/init.sql`

2. **System Flow Understanding**
    - Review the DFD in `docs/images/dfd.png`
    - Check system interactions in `docs/architecture/DFD.md`

3. **API Integration**
    - Review API documentation in `docs/api/endpoints.md`
    - Import Postman collection from `docs/api/postman/`

## Key Features

1. **User Management**
    - Multiple user roles (Admin, Doctor, Patient, Receptionist)
    - Role-based access control

2. **Appointment Management**
    - Schedule management
    - Online booking system
    - Token generation

3. **Medical Records**
    - Patient history
    - Prescriptions
    - Laboratory tests

4. **Billing System**
    - Consultation fees
    - Medicine charges
    - Laboratory charges

## Database Schema Highlights

The system uses a relational database with the following key entities:
- Users (Base entity for all system users)
- Staff (Extends Users)
- Doctors (Extends Staff)
- Patients
- Appointments
- Medical Records
- Prescriptions
- Billing

For detailed schema information, see [Database Documentation](database/README.md).

## API Structure

The API is organized into the following main sections:
- Authentication (/api/auth/*)
- User Management (/api/users/*)
- Appointment Management (/api/appointments/*)
- Medical Records (/api/records/*)
- Billing (/api/bills/*)

For detailed API documentation, see [API Documentation](api/endpoints.md).