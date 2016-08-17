package org.openexchange.integration;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.MockitoAnnotations.initMocks;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class CurrencyLayerTest {
    @Autowired
    private CurrencyLayerServiceImpl currencyService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testCurrencyList() throws Exception {
        Currencies currencies = currencyService.all();
        Assert.assertNotNull(currencies);
        Assert.assertEquals(168, currencies.getCurrencies().size());
    }

    @Test
    public void testQuotes() throws Exception {
        Quotes quotes = currencyService.live(Arrays.asList("USD", "EUR"));
        Assert.assertNotNull(quotes);
        Assert.assertEquals("USD", quotes.getSource());
        Assert.assertEquals(2, quotes.getQuotes().size());
    }
}
