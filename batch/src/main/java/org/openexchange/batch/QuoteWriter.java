package org.openexchange.batch;

import org.openexchange.jms.QueueProducer;
import org.openexchange.protocol.Quote;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuoteWriter implements ItemWriter<Quote> {
    private final QueueProducer queueProducer;

    @Autowired
    public QuoteWriter(QueueProducer queueProducer) {
        this.queueProducer = queueProducer;
    }

    @Override
    public void write(List<? extends Quote> list) throws Exception {
        list.forEach(queueProducer::send);
    }
}
