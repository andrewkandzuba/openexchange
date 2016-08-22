package org.openexchange.batch;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openexchange.integration.Currencies;
import org.openexchange.integration.CurrencyLayerService;
import org.openexchange.integration.Quotes;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class CurrencyLayerBatchTest {
    @Autowired
    private ItemReader<Quote> reader;
    @MockBean
    private CurrencyLayerService currencyLayerService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testReader() throws Exception {
        Currencies currencies = new Currencies();
        currencies.setCurrencies(Map.of("USD", "United States Dollar", "EUR", "European Euro"));
        when(currencyLayerService.all()).thenReturn(currencies);

        Quotes quotes = new Quotes();
        quotes.setSuccess(true);
        quotes.setSource("USD");
        quotes.setQuotes(Map.of("USDUSD", 1.00, "USDEUR", 0.90));

        when(currencyLayerService.live(Mockito.anyListOf(String.class))).thenReturn(quotes);
        Assert.assertNotNull(reader.read());
        Assert.assertNotNull(reader.read());
        Assert.assertNull(reader.read());
    }
}