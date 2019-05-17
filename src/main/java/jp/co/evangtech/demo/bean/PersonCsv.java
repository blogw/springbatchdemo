package jp.co.evangtech.demo.bean;

import lombok.Data;
import org.springframework.batch.item.ItemCountAware;

@Data
public class PersonCsv implements ItemCountAware {
    private int line;

    private int age;

    private String name;

    @Override
    public void setItemCount(int i) {
        this.line = i + 1;
    }
}
