package jp.co.evangtech.demo.schedule;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job taskletJob;

    @Autowired
    Job chunkJob;

    @Value("${job.tasklet.schedule.enable}")
    private boolean job1Enable = true;

    @Value("${job.chunk.schedule.enable}")
    private boolean job2Enable = true;

    @Scheduled(fixedRateString = "${job.tasklet.schedule.fixed_rate}")
    public void executeJob1() throws Exception {
        if (job1Enable) {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(taskletJob, jobParameters);
        }
    }

    @Scheduled(cron = "${job.chunk.schedule.cron}")
    public void executeJob2() throws Exception {
        if (job2Enable) {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("csvfile", "person.csv")
                    .toJobParameters();
            jobLauncher.run(chunkJob, jobParameters);
        }
    }
}