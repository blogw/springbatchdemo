package jp.co.evangtech.demo.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PersonTasklet implements Tasklet {
    private final static Logger log = LoggerFactory.getLogger(PersonTasklet.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String DELETE_DUPLICATE = "DELETE FROM person A USING person b WHERE A.ID > b.ID\tAND A.NAME = b.NAME AND A.age = b.age";

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info(DELETE_DUPLICATE);
        jdbcTemplate.execute(DELETE_DUPLICATE);
        return RepeatStatus.FINISHED;
    }
}
