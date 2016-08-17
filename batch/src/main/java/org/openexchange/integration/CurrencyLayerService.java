package org.openexchange.integration;

import java.util.List;

public interface CurrencyLayerService {
    Currencies all();
    Quotes live(List<String> currenciesCodes);
}
