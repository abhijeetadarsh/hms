package online.abhijeetadarsh.hms.service.impl;

import online.abhijeetadarsh.hms.common.User;
import online.abhijeetadarsh.hms.model.*;
import online.abhijeetadarsh.hms.repository.AdminRepository;
import online.abhijeetadarsh.hms.repository.DoctorRepository;
import online.abhijeetadarsh.hms.repository.PatientRepository;
import online.abhijeetadarsh.hms.repository.ReceptionistRepository;
import online.abhijeetadarsh.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ReceptionistRepository receptionistRepository;

    @Autowired
    public UserServiceImpl(
            AdminRepository adminRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            ReceptionistRepository receptionistRepository
    ) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.receptionistRepository = receptionistRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return admin.map(user -> (User) user);
        }

        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        if (doctor.isPresent()) {
            return doctor.map(user -> (User) user);
        }

        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isPresent()) {
            return patient.map(user -> (User) user);
        }

        Optional<Receptionist> receptionist = receptionistRepository.findByEmail(email);
        return receptionist.map(user -> (User) user);
    }

    @Override
    public Optional<User> findById(String userId) {
        Optional<Admin> admin = adminRepository.findById(userId);
        if (admin.isPresent()) {
            return admin.map(user -> (User) user);
        }

        Optional<Doctor> doctor = doctorRepository.findById(userId);
        if (doctor.isPresent()) {
            return doctor.map(user -> (User) user);
        }

        Optional<Patient> patient = patientRepository.findById(userId);
        if (patient.isPresent()) {
            return patient.map(user -> (User) user);
        }

        Optional<Receptionist> receptionist = receptionistRepository.findById(userId);
        return receptionist.map(user -> (User) user);
    }
}
