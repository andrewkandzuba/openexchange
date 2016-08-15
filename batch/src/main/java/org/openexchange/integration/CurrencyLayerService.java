package org.openexchange.integration;

import org.openexchange.protocol.Currencies;
import org.openexchange.protocol.Quotes;

import java.util.List;

public interface CurrencyLayerService {
    Currencies all();
    Quotes live(List<String> currenciesCodes);
}
