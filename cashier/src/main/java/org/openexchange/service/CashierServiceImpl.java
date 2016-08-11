package org.openexchange.service;

import org.openexchange.client.CurrencyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CashierServiceImpl implements CashierService {
    @Autowired
    private CurrencyClient currencyClient;

    @Override
    public BigDecimal exchange(String source, String target, BigDecimal amount) {
        BigDecimal quote = currencyClient.findRate(source, target);
        return amount.multiply(quote);
    }
}
