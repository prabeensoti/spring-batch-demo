package org.miu.cs590.springbatchdemo.serviceimpl;

import org.miu.cs590.springbatchdemo.repository.StudentRepository;
import org.miu.cs590.springbatchdemo.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Long getTotalStudentCount() {
        return studentRepository.count();
    }
}
