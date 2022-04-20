package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        // check if email is taken
        Boolean existEmail = studentRepository.selectExistEmail(student.getEmail());
        if(existEmail) {
            throw new BadRequestException(
                    "Email " + student.getEmail() + " taken"
            );
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        Student findStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if(!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(
                    "Student with id " + studentId + " does not exists"
            );
        }

        studentRepository.delete(findStudent);
    }
}
