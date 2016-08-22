package org.openexchange.batch;

import org.openexchange.integration.CurrencyLayerService;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class QuoteReader implements ItemReader<Quote> {
    @Autowired
    private CurrencyLayerService currencyLayerService;
    private final ThreadLocal<Iterator<Quote>> iterator = new ThreadLocal<>();

    @Override
    public Quote read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator.get() == null) {
            List<Quote> quotes = new ArrayList<>();
            List<String> currencyCodes = Arrays.asList(currencyLayerService.all().getCurrencies().keySet().stream().toArray(String[]::new));
            currencyLayerService
                    .live(currencyCodes)
                    .getQuotes()
                    .entrySet()
                    .forEach(entry -> quotes.add(new Quote(entry.getKey().substring(0, 3), entry.getKey().substring(3, 6), entry.getValue())));
            iterator.set(quotes.iterator());
        }
        if (iterator.get().hasNext()) {
            return iterator.get().next();
        }
        iterator.set(null);
        return null;
    }
}
