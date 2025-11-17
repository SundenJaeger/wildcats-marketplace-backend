package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.Student;
import com.rentoki.wildcatsmplacebackend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@Tag(name = "2. Student Management", description = "Student account management APIs")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Get all students",
            description = "Retrieve a list of all registered students (Admin only)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved students",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class)))
    )
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @Operation(
            summary = "Get student by ID",
            description = "Retrieve a specific student by their ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student found",
                    content = @Content(schema = @Schema(implementation = Student.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Student not found\"}")
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Integer id) {
        Student student = studentService.getStudentById(id);

        return ResponseEntity.ok(student);
    }

    @Operation(
            summary = "Update student verification status",
            description = "Activate or deactivate a student account (Admin only)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Verification status updated",
                    content = @Content(schema = @Schema(implementation = Student.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Student not found\"}")
                    )
            )
    })
    @PatchMapping("/{id}/verification")
    public ResponseEntity<Map<String, Object>> updateVerificationStatus(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Verification status",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isVerified": true
                                            }
                                            """
                            )
                    )
            )
            @RequestBody VerificationStatusRequest request) {
        Student student = studentService.updateStudentVerification(id, request.isVerified());

        Map<String, Object> response = Map.of(
                "studentId", student.getStudentId(),
                "isVerified", student.getIsVerified(),
                "message", "Student verification status updated successfully"
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get students by verification status",
            description = "Filter students by their verification status (Admin only)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved students",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class)))
    )
    @GetMapping("/verified/{isVerified}")
    public ResponseEntity<List<Student>> getStudentsByVerificationStatus(
            @Parameter(description = "Verification status", required = true, example = "true")
            @PathVariable Boolean isVerified) {
        List<Student> students = studentService.getStudentsByVerificationStatus(isVerified);
        return ResponseEntity.ok(students);
    }

    public record VerificationStatusRequest(Boolean isVerified) {
    }
}