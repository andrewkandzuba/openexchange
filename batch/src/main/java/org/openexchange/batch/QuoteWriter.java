package org.openexchange.batch;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class QuoteWriter implements ItemWriter<Quote> {
    @Override
    public void write(List<? extends Quote> list) throws Exception {
        list.forEach(o -> System.out.println(o.getSource() + " => " + o.getTarget() + " = " + o.getQuote()));
    }
}
