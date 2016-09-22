package org.openexchange.jobs;

import org.openexchange.integration.CurrencyLayerService;
import org.openexchange.integration.ServiceException;
import org.openexchange.jms.JmsQuotesTransactionalService;
import org.openexchange.protocol.Quote;
import org.openexchange.protocol.Quotes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@RefreshScope
public class CurrencylayerConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(CurrencylayerConfiguration.class);

    private final CurrencyLayerService currencyLayerService;
    private final JmsQuotesTransactionalService jmsQuotesTransactionalService;

    public CurrencylayerConfiguration(CurrencyLayerService currencyLayerService, JmsQuotesTransactionalService jmsQuotesTransactionalService) {
        this.currencyLayerService = currencyLayerService;
        this.jmsQuotesTransactionalService = jmsQuotesTransactionalService;
    }

    @Job(concurrencyString = "1")
    public void update(){
        try {
            List<String> currencyCodes = Arrays.asList(currencyLayerService.all().getCurrencies().keySet().stream().toArray(String[]::new));
            Quotes response = currencyLayerService.live(currencyCodes);
            Date timestamp = new Date(response.getTimestamp());
            List<Quote> quotes = new CopyOnWriteArrayList<>();
            response.getQuotes()
                    .entrySet()
                    .forEach(entry -> quotes.add(build(entry.getKey().substring(0, 3), entry.getKey().substring(3, 6), entry.getValue(), timestamp)));
            jmsQuotesTransactionalService.write(quotes);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }
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