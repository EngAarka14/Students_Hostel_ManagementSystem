package com.hostel.management.service;

import com.hostel.management.entity.Student;
import com.hostel.management.exception.BadRequestException;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Transactional
    public Student create(Student student) {
        if (studentRepository.existsByStudentNumber(student.getStudentNumber())) {
            throw new BadRequestException("A student with this student number already exists");
        }
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new BadRequestException("A student with this email already exists");
        }
        return studentRepository.save(student);
    }

    @Transactional
    public Student update(Long id, Student updated) {
        Student existing = getById(id);
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setGender(updated.getGender());
        existing.setCourse(updated.getCourse());
        return studentRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Student existing = getById(id);
        studentRepository.delete(existing);
    }
}
