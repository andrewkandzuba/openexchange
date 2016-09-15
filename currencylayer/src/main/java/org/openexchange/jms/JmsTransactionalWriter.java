package org.openexchange.jms;

import org.openexchange.protocol.Quote;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

class JmsTransactionalWriter implements ItemWriter<Quote> {
    private final JmsTransactionalService service;

    JmsTransactionalWriter(JmsTransactionalService service) {
        this.service = service;
    }

    @Override
    public void write(List<? extends Quote> list) throws Exception {
        service.write(list);
    }
}