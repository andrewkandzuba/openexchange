package org.openexchange.batch;


import org.openexchange.domain.Quote;
import org.springframework.batch.item.ItemProcessor;

public class QuoteProcessor implements ItemProcessor<Quote, Quote> {
    @Override
    public Quote process(Quote quote) throws Exception {
        return quote;
    }
}
