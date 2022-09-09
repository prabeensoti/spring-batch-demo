package org.miu.cs590.springbatchdemo.controller;

import org.miu.cs590.springbatchdemo.service.BatchExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchExecutorController {
    private final BatchExecutor batchExecutor;

    public BatchExecutorController(BatchExecutor batchExecutor) {
        this.batchExecutor = batchExecutor;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/batch/execute")
    public void executeBatchProcess(){
        batchExecutor.runBatchProcess();
    }
}
