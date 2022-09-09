package org.miu.cs590.springbatchdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentCSV {
    private String firstName;
    private String lastName;
    private Double gpa;
    private Integer age;
}
