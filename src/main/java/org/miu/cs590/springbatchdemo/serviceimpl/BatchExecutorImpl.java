package org.miu.cs590.springbatchdemo.serviceimpl;

import org.miu.cs590.springbatchdemo.service.BatchExecutor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class BatchExecutorImpl implements BatchExecutor {
    private final JobLauncher jobLauncher;
    private final TaskExecutor simpleAsyncTaskExecutor;
    private final Job job;

    public BatchExecutorImpl(JobLauncher jobLauncher, TaskExecutor simpleAsyncTaskExecutor, @Qualifier("saveStudentJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.simpleAsyncTaskExecutor = simpleAsyncTaskExecutor;
        this.job = job;
    }

    @Override
    public void runBatchProcess() {
        simpleAsyncTaskExecutor.execute(()-> {
            try{
                JobExecution jobExecution = jobLauncher.run(job,new JobParameters());
            } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                     JobParametersInvalidException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
