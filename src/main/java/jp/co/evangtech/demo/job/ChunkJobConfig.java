package jp.co.evangtech.demo.job;

import jp.co.evangtech.demo.bean.PersonCsv;
import jp.co.evangtech.demo.entity.Person;
import jp.co.evangtech.demo.listener.ChunkJobReaderListener;
import jp.co.evangtech.demo.listener.ChunkJobSkipListener;
import jp.co.evangtech.demo.processor.ChunkJobProcessor;
import jp.co.evangtech.demo.repository.PersonRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
class ChunkJobConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private ChunkJobReaderListener readerListener;

    @Autowired
    private ChunkJobSkipListener skipListener;

    @Autowired
    private ChunkJobProcessor chunkJobProcessor;

    @Value("${job.chunk.schedule.skip_limit}")
    private int skipLimit = 5;

    @Value("${job.chunk.schedule.chunk}")
    private int chunk = 5;

    @Bean
    @StepScope
    public FlatFileItemReader<PersonCsv> chunkJobReader(@Value("#{jobParameters[csvfile]}") String csvfile) {
        return new FlatFileItemReaderBuilder<PersonCsv>()
                .name("chunkJobReader")
                .resource(new ClassPathResource(csvfile))
                .targetType(PersonCsv.class)
                .linesToSkip(1)
                .delimited().delimiter(",").names(new String[]{"name", "age"})
                .build();
    }

    @Bean
    public ItemProcessor<PersonCsv, Person> compProcessor() {
        CompositeItemProcessor<PersonCsv, Person> processor = new CompositeItemProcessor<>();
        List<ItemProcessor<PersonCsv, Person>> listProcessor = new ArrayList<>();
        listProcessor.add(chunkJobProcessor);
        processor.setDelegates(listProcessor);
        return processor;
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Person> chunkJobWriter() {
        return new RepositoryItemWriterBuilder<Person>()
                .methodName("save")
                .repository(repository)
                .build();
    }

    @Bean
    public Step chunkJobStep(ItemReader<PersonCsv> chunkJobReader, RepositoryItemWriter<Person> chunkJobWriter) {
        return steps.get("chunkJobStep")
                .<PersonCsv, Person>chunk(chunk)
                .listener(readerListener)
                .reader(chunkJobReader)
                .faultTolerant()
                .skipLimit(skipLimit)
                .skip(ValidationException.class)
                .skip(FlatFileParseException.class)
                .listener(skipListener)
                .processor(chunkJobProcessor)
                .writer(chunkJobWriter)
                .build();
    }

    @Bean
    public Job chunkJob(Step chunkJobStep) {
        return jobs
                .get("chunkJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkJobStep)
                .build();
    }
}
