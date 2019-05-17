package jp.co.evangtech.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

@Component
public class ChunkJobSkipListener implements SkipListener {
    private final static Logger log = LoggerFactory.getLogger("chunk_job_skip");

    @Override
    public void onSkipInRead(Throwable throwable) {
        if (throwable instanceof FlatFileParseException) {
            FlatFileParseException exp = (FlatFileParseException) throwable;
            log.warn("csv line {} skipped, {}", exp.getLineNumber(), exp.getInput());
        } else {
            log.warn(throwable.getMessage());
        }
    }

    @Override
    public void onSkipInWrite(Object o, Throwable throwable) {

    }

    @Override
    public void onSkipInProcess(Object o, Throwable throwable) {

    }
}
