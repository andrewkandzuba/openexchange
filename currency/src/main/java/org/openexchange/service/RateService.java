package org.openexchange.service;

import org.openexchange.domain.Currency;
import org.openexchange.domain.Rate;

import java.math.BigDecimal;
import java.util.List;

public interface RateService {
    List<Rate> findRatesBySource(Currency source);
    Rate findRate(Currency source, Currency target);
    Rate create(Currency source, Currency target, BigDecimal quote);
    void saveChanges(Rate rate);
    void delete(Currency source, Currency target);
}
