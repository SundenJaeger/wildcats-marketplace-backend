package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.InactiveAccountException;
import com.rentoki.wildcatsmplacebackend.exceptions.InvalidCredentialsException;
import com.rentoki.wildcatsmplacebackend.exceptions.StudentNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.*;
import com.rentoki.wildcatsmplacebackend.repository.StudentRepository;
import com.rentoki.wildcatsmplacebackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Integer id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public Student createStudent(Student student, RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(extractFirstName(registerRequest.getFirstName()));
        user.setLastName(extractLastName(registerRequest.getLastName()));
        user.setType("S");

        User savedUser = userService.createUser(user);

        student.setStudentId(savedUser.getUserId());
        student.setUser(savedUser);
        student.setIsVerified(false);

        return studentRepository.save(student);
    }

    public Student updateStudent(Integer id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND.getMessage()));

        student.setEnrollmentStatus(studentDetails.getEnrollmentStatus());
        student.setProgram(studentDetails.getProgram());
        student.setYearLevel(studentDetails.getYearLevel());
        student.setIsVerified(studentDetails.getIsVerified());

        return studentRepository.save(student);
    }

    public void deleteStudent(Integer id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND.getMessage()));
        studentRepository.delete(student);
    }

    public LoginResponse loginStudent(LoginRequest loginRequest) {
        Optional<User> userOptional;

        if (loginRequest.getUsername() != null && loginRequest.getUsername().contains("@")) {
            userOptional = userRepository.findByEmail(loginRequest.getUsername());
        } else {
            userOptional = userRepository.findByUsername(loginRequest.getUsername());
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Optional<Student> studentOptional = studentRepository.findById(user.getUserId());
            if (studentOptional.isEmpty()) {
                throw new InvalidCredentialsException(ErrorMessages.INVALID_CREDENTIALS.getMessage());
            }

            Student student = studentOptional.get();

            if (!student.getIsVerified()) {
                throw new InactiveAccountException(ErrorMessages.INACTIVE_ACCOUNT.getMessage());
            }

            if (user.getPassword().equals(loginRequest.getPassword())) {
                LoginResponse response = new LoginResponse();
                response.setUsername(user.getUsername());
                response.setEmail(user.getEmail());
                return response;
            } else {
                throw new InvalidCredentialsException(ErrorMessages.INVALID_CREDENTIALS.getMessage());
            }
        }

        throw new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND.getMessage());
    }

    public RegisterResponse registerStudent(RegisterRequest registerRequest) {
        Student student = new Student();
        student.setEnrollmentStatus("Active");
        student.setProgram("Undecided");
        student.setYearLevel("Freshman");

        Student savedStudent = createStudent(student, registerRequest);

        return new RegisterResponse(savedStudent.getUser(), savedStudent);
    }

    public Student updateStudentVerification(Integer studentId, Boolean isVerified) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND.getMessage()));

        student.setIsVerified(isVerified);
        return studentRepository.save(student);
    }

    public List<Student> getStudentsByVerificationStatus(Boolean isVerified) {
        return studentRepository.findByIsVerified(isVerified);
    }

    private String extractFirstName(String fullname) {
        if (fullname == null || fullname.trim().isEmpty()) {
            return "";
        }
        String[] names = fullname.trim().split("\\s+");
        return names[0];
    }

    private String extractLastName(String fullname) {
        if (fullname == null || fullname.trim().isEmpty()) {
            return "";
        }
        String[] names = fullname.trim().split("\\s+");
        if (names.length > 1) {
            return names[names.length - 1];
        }
        return "";
    }
}