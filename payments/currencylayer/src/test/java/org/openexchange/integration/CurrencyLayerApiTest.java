package org.openexchange.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.openexchange.pojos.Currencies;
import org.openexchange.pojos.Quotes;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyLayerApiTest.class)
@SpringBootApplication
@ComponentScan(basePackages = "org.openexchange.integration")
@TestPropertySource(locations = "classpath:test.properties")
public class CurrencyLayerApiTest {
    @InjectMocks
    private CurrencyLayerServiceImpl currencyService;
    @MockBean
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testCurrencyList() throws Exception {
        Currencies currencies = new Currencies();
        currencies.setSuccess(true);
        currencies.setCurrencies(Map.of("USD", "United States Dollar"));

        when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(), Matchers.<Object>anyVararg())).thenReturn(new ResponseEntity<>(currencies, HttpStatus.OK));

        Currencies resp = currencyService.all();
        Assert.assertNotNull(resp);
        Assert.assertTrue(resp.getSuccess());
        Assert.assertEquals(1, currencies.getCurrencies().size());
        Assert.assertEquals("United States Dollar", resp.getCurrencies().get("USD"));
    }

    @Test
    public void testQuotes() throws Exception {
        Quotes quotes = new Quotes();
        quotes.setSuccess(true);
        quotes.setSource("USD");
        quotes.setQuotes(Map.of("USDUSD", 1.00, "USDEUR", 0.90));
        List<String> params = Arrays.asList("USD", "EUR");

        when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(), Matchers.<Object>anyVararg())).thenReturn(new ResponseEntity<>(quotes, HttpStatus.OK));

        Quotes resp = currencyService.live(params);
        Assert.assertNotNull(quotes);
        Assert.assertTrue(resp.getSuccess());
        Assert.assertEquals("USD", quotes.getSource());
        Assert.assertEquals(2, quotes.getQuotes().size());
    }
}
