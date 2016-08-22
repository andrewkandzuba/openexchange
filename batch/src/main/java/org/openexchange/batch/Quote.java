package org.openexchange.batch;

public class Quote {
    private final String source;
    private final String target;
    private final Double quote;

    public Quote(String source, String target, Double quote) {
        this.source = source;
        this.target = target;
        this.quote = quote;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public Double getQuote() {
        return quote;
    }
}
