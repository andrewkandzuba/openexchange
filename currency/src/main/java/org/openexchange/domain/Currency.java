package org.openexchange.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AutoProperty
public class Currency implements Serializable {
    private static final long serialVersionUID = -4875874173742367410L;
    @Id
    @Column(length = 3, unique = true, nullable = false)
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
