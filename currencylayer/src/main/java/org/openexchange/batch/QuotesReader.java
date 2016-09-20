package org.openexchange.batch;

import org.openexchange.integration.CurrencyLayerService;
import org.openexchange.integration.ServiceException;
import org.openexchange.protocol.Quote;
import org.openexchange.protocol.Quotes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class QuotesReader implements ItemReader<Quote>, ItemStream {
    private static final Logger logger = LoggerFactory.getLogger(QuotesReader.class);
    private static final String CURRENT_INDEX = "quotes.reader.current.index";
    private final List<Quote> quotes = new CopyOnWriteArrayList<>();
    private final CurrencyLayerService currencyLayerService;
    private volatile int currentIndex = 0;

    @Autowired
    public QuotesReader(CurrencyLayerService currencyLayerService) {
        this.currencyLayerService = currencyLayerService;
    }

    @Override
    public Quote read() throws Exception {
        if (currentIndex < quotes.size()) {
            return quotes.get(currentIndex++);
        }
        return null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey(CURRENT_INDEX)) {
            currentIndex = executionContext.getInt(CURRENT_INDEX);
        } else {
            try {
                currentIndex = 0;
                List<String> currencyCodes = Arrays.asList(currencyLayerService.all().getCurrencies().keySet().stream().toArray(String[]::new));
                Quotes response = currencyLayerService.live(currencyCodes);
                Date timestamp = new Date(response.getTimestamp());
                response.getQuotes()
                        .entrySet()
                        .forEach(entry -> quotes.add(build(entry.getKey().substring(0, 3), entry.getKey().substring(3, 6), entry.getValue(), timestamp)));
            } catch (ServiceException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putInt(CURRENT_INDEX, currentIndex);
    }

    @Override
    public void close() throws ItemStreamException {
        quotes.clear();
    }

    private static Quote build(String source, String target, Double rate, Date timestamp) {
        Quote quote = new Quote();
        quote.setSource(source);
        quote.setTarget(target);
        quote.setQuote(rate);
        quote.setTimestamp(timestamp);
        return quote;
    }
}