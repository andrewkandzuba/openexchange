package org.openexchange.service;

import org.openexchange.domain.Currency;
import org.openexchange.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Repository
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    @Transactional(readOnly=true)
    public Iterable<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public Currency findByCode(String code) {
        return currencyRepository.findOne(code);
    }

    @Override
    @Transactional
    public Currency create(String code, String description) {
        Assert.notNull(code, "currency's code cannot take null value");
        Assert.notNull(description, "currency's description cannot take null value");

        Assert.isNull(currencyRepository.findOne(code), "currency already exists: " + code);
        Currency currency = new Currency(code, description);
        currencyRepository.save(currency);
        return currency;
    }

    @Override
    @Transactional
    public void saveChanges(Currency currency) {
        Assert.notNull(currency, "currency cannot take null value");
        Assert.notNull(currency.getCode(), "currency's code cannot take null value");
        Assert.notNull(currency.getDescription(), "currency's description cannot take null value");
        Assert.notNull(currencyRepository.findOne(currency.getCode()), "can't find currency of with code " + currency.getCode());
        currencyRepository.save(currency);
    }

    @Override
    @Transactional
    public void delete(Currency currency) {
        Assert.notNull(currencyRepository.findOne(currency.getCode()), "can't find currency of with code " + currency.getCode());
        currencyRepository.delete(currency);
    }
}