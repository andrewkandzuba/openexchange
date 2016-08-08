package org.openexchange.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AutoProperty
public class Currency {
    @Id
    private String code;
    @NotEmpty
    private String description;

    public Currency() {
    }

    public Currency(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
