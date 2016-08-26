package org.openexchange.batch;

import org.openexchage.domain.Quote;
import org.openexchange.integration.CurrencyLayerService;
import org.openexchange.integration.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class QuoteReader implements ItemReader<Quote>, ItemStream {
    private static final Logger logger = LoggerFactory.getLogger(QuoteReader.class);
    private static final String CURRENT_INDEX = "current.index";
    private final List<Quote> quotes = new CopyOnWriteArrayList<>();
    private volatile int currentIndex = 0;

    @Autowired
    private CurrencyLayerService currencyLayerService;

    @Override
    public Quote read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (currentIndex < quotes.size()) {
            return quotes.get(currentIndex++);
        }
        return null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if(executionContext.containsKey(CURRENT_INDEX)){
            currentIndex = executionContext.getInt(CURRENT_INDEX);
        }
        else{
            try {
                currentIndex = 0;
                List<String> currencyCodes = Arrays.asList(currencyLayerService.all().getCurrencies().keySet().stream().toArray(String[]::new));
                currencyLayerService
                        .live(currencyCodes)
                        .getQuotes()
                        .entrySet()
                        .forEach(entry -> quotes.add(new Quote(entry.getKey().substring(0, 3), entry.getKey().substring(3, 6), entry.getValue())));
            } catch (ServiceException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putLong(CURRENT_INDEX, currentIndex);
    }

    @Override
    public void close() throws ItemStreamException {
        quotes.clear();
    }
}
