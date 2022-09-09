package org.miu.cs590.springbatchdemo.config;

import org.miu.cs590.springbatchdemo.entity.Student;
import org.miu.cs590.springbatchdemo.entity.StudentCSV;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

@Configuration
@EnableBatchProcessing
public class BatchProcessConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    @Value("${input.file}")
    private String fileName;

    public BatchProcessConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public FlatFileItemReader<StudentCSV> itemReader(){
        return new FlatFileItemReaderBuilder<StudentCSV>()
                .name("studentItemReader")
                .resource(new ClassPathResource(fileName))
                .delimited()
                .names("firstName","lastName","gpa","age")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<StudentCSV>(){{
                    setTargetType(StudentCSV.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<StudentCSV, Student> itemProcessor(){
        return studentCSV -> new Student(null,studentCSV.getFirstName(),studentCSV.getLastName(),studentCSV.getGpa(), LocalDate.ofYearDay(LocalDate.now().minusYears(studentCSV.getAge()+1).getYear(),1));
    }

    @Bean
    public JpaItemWriter<Student> jpaItemWriter(){
        return new JpaItemWriterBuilder<Student>().entityManagerFactory(entityManagerFactory).build();
    }
    @Bean
    public Job saveStudentJob(Step step){
        return jobBuilderFactory.get("saveStudentJob").incrementer(new RunIdIncrementer()).flow(step).end().build();
    }

    @Bean
    public Step step(){
        return stepBuilderFactory.get("step").<StudentCSV,Student>chunk(20).reader(itemReader()).processor(itemProcessor()).writer(jpaItemWriter()).build();
    }
}
