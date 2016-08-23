package org.openexchange.integration;

import java.util.List;

public interface CurrencyLayerService {
    Currencies all() throws ServiceException;
    Quotes live(List<String> currencyCodes) throws ServiceException;
}
