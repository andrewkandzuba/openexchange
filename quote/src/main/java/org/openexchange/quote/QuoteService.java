package org.openexchange.quote;

import org.openexchange.protocol.Quote;

import java.util.List;

public interface QuoteService {
    void write(List<? extends Quote> list);
}
