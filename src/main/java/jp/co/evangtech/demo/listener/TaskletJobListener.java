package jp.co.evangtech.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class TaskletJobListener implements JobExecutionListener {
    private final static Logger log = LoggerFactory.getLogger(TaskletJobListener.class);


    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("=========start=========");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("=========finish=========");
    }
}
