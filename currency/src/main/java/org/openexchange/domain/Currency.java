package org.openexchange.domain;

public class Currency {
    private final String code;
    private final String description;

    public Currency(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
