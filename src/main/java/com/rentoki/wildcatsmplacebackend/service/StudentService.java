package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.InactiveAccountException;
import com.rentoki.wildcatsmplacebackend.exceptions.InvalidCredentialsException;
import com.rentoki.wildcatsmplacebackend.exceptions.StudentNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.*;
import com.rentoki.wildcatsmplacebackend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public LoginResponse loginStudent(LoginRequest loginRequest) {
        Optional<Student> studentOptional;

        if (loginRequest.getUsername() != null && loginRequest.getUsername().contains("@")) {
            studentOptional = studentRepository.findByEmail(loginRequest.getUsername());
        } else {
            studentOptional = studentRepository.findByUsername(loginRequest.getUsername());
        }

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            if (!student.isActive()) {
                throw new InactiveAccountException(ErrorMessages.INACTIVE_ACCOUNT.getMessage());
            }

            if (student.getPassword().equals(loginRequest.getPassword())) {
                LoginResponse response = new LoginResponse();
                response.setUsername(student.getUsername());
                response.setEmail(student.getEmail());

                return response;
            } else {
                throw new InvalidCredentialsException(ErrorMessages.INVALID_CREDENTIALS.getMessage());
            }
        }

        throw new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND.getMessage());
    }

    public RegisterResponse registerStudent(RegisterRequest registerRequest) {
        Student student = new Student(registerRequest);
        studentRepository.save(student);
        return new RegisterResponse(registerRequest);
    }
}
