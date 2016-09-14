package org.openexchange.jms;

import org.openexchange.protocol.Quote;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JmsTransactionalWriter implements ItemWriter<Quote> {
    private final JmsTransactionalService service;

    @Autowired
    public JmsTransactionalWriter(JmsTransactionalService service) {
        this.service = service;
    }

    @Override
    public void write(List<? extends Quote> list) throws Exception {
        service.write(list);
    }
}
