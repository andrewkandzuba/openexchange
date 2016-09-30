package org.openexchange.quote;

import org.openexchange.pojos.Quote;

import java.util.Collection;

public interface QuoteService {
    void write(Collection<? extends Quote> list);
}
