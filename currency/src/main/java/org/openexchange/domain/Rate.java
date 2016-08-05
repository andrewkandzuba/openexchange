package org.openexchange.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Rate {
    private final String from;
    private final String to;
    private final BigDecimal rate;
    private final Date at;

    public Rate(String from, String to, BigDecimal rate, Date at) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.at = at;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Date getAt() {
        return at;
    }
}
