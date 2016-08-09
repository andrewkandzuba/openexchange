package org.exchange.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openexchange.CurrencyApplication;
import org.openexchange.domain.Currency;
import org.openexchange.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class CurrencyRepositoryTest {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testAddNewCurrency() throws Exception {
        Currency currency = new Currency("USD", "United States Dollar");
        currencyRepository.save(currency);
        Currency existing = currencyRepository.findOne("USD");
        Assert.assertNotNull(existing);
        Assert.assertEquals("USD", existing.getCode());
        Assert.assertEquals("United States Dollar", existing.getDescription());
        Assert.assertNull(currencyRepository.findOne("EUR"));
    }

    @Test
    public void testUpdateExistingCurrency() throws Exception {
        Currency currency = new Currency("EUR", "European Euro");
        currencyRepository.save(currency);
        Currency existing = currencyRepository.findOne("EUR");
        Assert.assertEquals("European Euro", existing.getDescription());
        existing.setDescription("European Union Euro");
        currencyRepository.save(existing);
        existing = currencyRepository.findOne("EUR");
        Assert.assertEquals("European Union Euro", existing.getDescription());
    }

    @Test
    public void testDeleteExistingCurrency() throws Exception {
        Currency currency = new Currency("EUR", "European Euro");
        currencyRepository.save(currency);
        Currency existing = currencyRepository.findOne("EUR");
        Assert.assertEquals("European Euro", existing.getDescription());
        currencyRepository.delete(existing);
        existing = currencyRepository.findOne("EUR");
        Assert.assertNull(existing);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testFailedWithNull() throws Exception {
        currencyRepository.findOne((String) null);
    }
}
