I'll explain all the API routes and their usage in a typical healthcare management system (HMS):

1. Authentication Routes (`/api/auth/`):
```
POST /api/auth/register
- Register a new user (patient/doctor/staff/admin)
- Body: {
    "username": "john_doe",
    "password": "securepass",
    "email": "john@example.com",
    "name": "John Doe",
    "gender": "MALE",
    "contactNumber": "1234567890",
    "address": "123 Street",
    "role": "PATIENT"
  }

POST /api/auth/login
- Login user
- Body: {
    "username": "john_doe",
    "password": "securepass"
  }
- Returns: JWT token + user details
```

2. User Management Routes (`/api/users/`):
```
GET /api/users/profile
- Get current user's profile
- Header: Authorization: Bearer <jwt_token>

PUT /api/users/profile
- Update current user's profile
- Header: Authorization: Bearer <jwt_token>
- Body: {
    "email": "new_email@example.com",
    "contactNumber": "new_number",
    "address": "new address"
  }

ADMIN ONLY:
GET /api/users
- Get all users
- Query params: role, page, size

GET /api/users/{id}
- Get specific user details

DELETE /api/users/{id}
- Delete user

PUT /api/users/{id}/role
- Update user role
- Body: { "role": "DOCTOR" }
```

3. Doctor Routes (`/api/doctors/`):
```
GET /api/doctors
- Get all doctors
- Query params: specialization, available, page, size

GET /api/doctors/{id}
- Get specific doctor details

GET /api/doctors/{id}/schedule
- Get doctor's schedule/availability

DOCTOR ONLY:
PUT /api/doctors/schedule
- Update availability
- Body: {
    "weekday": "MONDAY",
    "startTime": "09:00",
    "endTime": "17:00"
  }
```

4. Appointment Routes (`/api/appointments/`):
```
POST /api/appointments
- Book new appointment
- Body: {
    "doctorId": 1,
    "date": "2024-12-26",
    "timeSlot": "10:00",
    "description": "Regular checkup"
  }

GET /api/appointments
- Get user's appointments (as patient or doctor)
- Query params: status, from, to, page, size

GET /api/appointments/{id}
- Get specific appointment details

PUT /api/appointments/{id}/status
- Update appointment status
- Body: { "status": "CONFIRMED" }

DELETE /api/appointments/{id}
- Cancel appointment
```

5. Medical Records Routes (`/api/records/`):
```
DOCTOR ONLY:
POST /api/records
- Create medical record
- Body: {
    "patientId": 1,
    "diagnosis": "Fever",
    "prescription": "Paracetamol",
    "notes": "Follow up in 1 week"
  }

PATIENT/DOCTOR ACCESS:
GET /api/records
- Get medical records (patient sees own, doctor sees their patients')
- Query params: patientId, from, to, page, size

GET /api/records/{id}
- Get specific record details
```

6. Dashboard Routes (`/api/dashboard/`):
```
GET /api/dashboard/stats
- Get role-specific dashboard statistics
- For Admin: total users, appointments, etc.
- For Doctor: upcoming appointments, patient count
- For Patient: next appointment, medical history
```

Example Usage with curl:

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"pass","role":"PATIENT",...}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"pass"}'

# Use received token for protected routes
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer eyJhbGc..."

# Book appointment
curl -X POST http://localhost:8080/api/appointments \
  -H "Authorization: Bearer eyJhbGc..." \
  -H "Content-Type: application/json" \
  -d '{"doctorId":1,"date":"2024-12-26",...}'
```

Would you like me to:
1. Show the controller code for any specific route?
2. Explain the security permissions for different roles?
3. Add more specific routes for certain features?