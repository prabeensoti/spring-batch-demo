package org.miu.cs590.springbatchdemo.repository;

import org.miu.cs590.springbatchdemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
