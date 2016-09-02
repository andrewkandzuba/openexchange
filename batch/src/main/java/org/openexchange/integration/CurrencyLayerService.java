package org.openexchange.integration;

import org.openexchange.protocol.Currencies;
import org.openexchange.protocol.Quotes;

import java.util.List;

public interface CurrencyLayerService {
    Currencies all() throws ServiceException;
    Quotes live(List<String> currencyCodes) throws ServiceException;
}
