package org.openexchange.service;

import org.openexchange.domain.Currency;

public interface CurrencyService {
    Iterable<Currency> findAll();
    Currency findByCode(String code);
    Currency create(String code, String description);
    void saveChanges(Currency currency);
    void delete(Currency currency);
}
