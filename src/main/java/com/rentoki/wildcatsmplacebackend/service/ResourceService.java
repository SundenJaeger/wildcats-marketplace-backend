package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.ResourceNotFoundException;
import com.rentoki.wildcatsmplacebackend.exceptions.StudentNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.Resource;
import com.rentoki.wildcatsmplacebackend.model.Student;
import com.rentoki.wildcatsmplacebackend.repository.ResourceRepository;
import com.rentoki.wildcatsmplacebackend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final StudentRepository studentRepository;

    public ResourceService(ResourceRepository resourceRepository, StudentRepository studentRepository) {
        this.resourceRepository = resourceRepository;
        this.studentRepository = studentRepository;
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public List<Resource> getResourcesByStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND.getMessage());
        }
        return resourceRepository.findByStudentStudentId(studentId);
    }

    public List<Resource> getResourcesByCategory(Integer categoryId) {
        return resourceRepository.findByCategoryCategoryId(categoryId);
    }

    public List<Resource> getAvailableResources() {
        return resourceRepository.findByStatus(Resource.ResourceStatus.AVAILABLE);
    }

    public List<Resource> searchResources(String keyword) {
        return resourceRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Resource getResourceById(Integer id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND.getMessage()));
    }

    public Resource createResource(Resource resource) {
        Student student = studentRepository.findById(resource.getStudent().getStudentId())
                .orElseThrow(() -> new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND.getMessage()));

        if (!student.getIsVerified()) {
            throw new RuntimeException(ErrorMessages.STUDENT_NOT_VERIFIED.getMessage());
        }

        return resourceRepository.save(resource);
    }

    public Resource updateResource(Integer id, Resource resourceDetails) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND.getMessage()));

        resource.setTitle(resourceDetails.getTitle());
        resource.setDescription(resourceDetails.getDescription());
        resource.setPrice(resourceDetails.getPrice());
        resource.setCondition(resourceDetails.getCondition());
        resource.setStatus(resourceDetails.getStatus());
        resource.setCategory(resourceDetails.getCategory());

        return resourceRepository.save(resource);
    }

    public Resource updateResourceStatus(Integer id, Resource.ResourceStatus status) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND.getMessage()));

        resource.setStatus(status);
        if (status == Resource.ResourceStatus.SOLD) {
            resource.setDateSold(java.time.LocalDateTime.now());
        }

        return resourceRepository.save(resource);
    }

    public void deleteResource(Integer id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.RESOURCE_NOT_FOUND.getMessage()));
        resourceRepository.delete(resource);
    }

    public List<Resource> getResourcesByCondition(Resource.ItemCondition condition) {
        return resourceRepository.findByCondition(condition);
    }
}