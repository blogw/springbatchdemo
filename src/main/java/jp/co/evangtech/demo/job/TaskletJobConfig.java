package jp.co.evangtech.demo.job;

import jp.co.evangtech.demo.listener.TaskletJobListener;
import jp.co.evangtech.demo.tasklet.PersonTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class TaskletJobConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private PersonTasklet tasklet;

    @Autowired
    private TaskletJobListener listener;

    @Bean
    public Step taskletJobStep() {
        return steps.get("シンプルジョーブ　スデップ①")
                .tasklet(tasklet)
                .build();
    }

    @Bean
    public Job taskletJob(Step taskletJobStep) {
        return jobs
                .get("taskletJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(taskletJobStep)
                .build();
    }

}
