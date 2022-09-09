package org.miu.cs590.springbatchdemo.controller;

import org.miu.cs590.springbatchdemo.service.BatchExecutor;
import org.miu.cs590.springbatchdemo.service.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchExecutorController {
    private final BatchExecutor batchExecutor;
    private final StudentService studentService;

    public BatchExecutorController(BatchExecutor batchExecutor,StudentService studentService) {
        this.batchExecutor = batchExecutor;
        this.studentService=studentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/batch/execute")
    public String executeBatchProcess(){
        batchExecutor.runBatchProcess();
        return "BATCH JOB EXECUTED";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/data/count")
    public Long getDataCount(){
        return studentService.getTotalStudentCount();
    }
}
