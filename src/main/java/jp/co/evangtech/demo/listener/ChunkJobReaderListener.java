package jp.co.evangtech.demo.listener;

import jp.co.evangtech.demo.bean.PersonCsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class ChunkJobReaderListener implements ItemReadListener<PersonCsv> {
    private final static Logger log = LoggerFactory.getLogger(ChunkJobReaderListener.class);

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(PersonCsv personCsv) {
        log.info("Read CSV: {}", personCsv);
    }

    @Override
    public void onReadError(Exception e) {

    }
}
