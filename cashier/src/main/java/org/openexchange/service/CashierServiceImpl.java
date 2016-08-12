package org.openexchange.service;

import org.openexchange.client.CurrencyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CashierServiceImpl implements CashierService {
    private final CurrencyClient currencyClient;

    @Autowired
    public CashierServiceImpl(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @Override
    public BigDecimal exchange(String source, String target, BigDecimal amount) {
        BigDecimal quote = currencyClient.findQuote(source, target);
        return amount.multiply(quote);
    }
}
