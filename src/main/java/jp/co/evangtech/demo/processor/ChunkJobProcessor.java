package jp.co.evangtech.demo.processor;

import jp.co.evangtech.demo.bean.PersonCsv;
import jp.co.evangtech.demo.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Component
public class ChunkJobProcessor implements ItemProcessor<PersonCsv, Person> {
    private final static Logger log = LoggerFactory.getLogger(ChunkJobProcessor.class);
    private final static Logger skipLog = LoggerFactory.getLogger("chunk_job_skip");

    @Override
    public Person process(final PersonCsv personCsv) throws Exception {
        // validate
        if (personCsv.getAge() < 20) {
            skipLog.warn("csv line {} skipped, age is less than 20", personCsv.getLine());
            return null;
        } else {
            Person person = new Person();
            person.setAge(personCsv.getAge());
            person.setName(personCsv.getName());
            log.info("Processing Record: {}", person);
            return person;
        }
    }
}