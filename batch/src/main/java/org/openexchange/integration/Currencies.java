package org.openexchange.integration;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;


/**
 * List of supported currencies
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "success",
        "error",
        "terms",
        "privacy",
        "currencies"
})
public class Currencies {

    /**
     * (Required)
     */
    @NotNull
    @JsonProperty("success")
    private Boolean success;
    /**
     * Error details
     */
    @JsonProperty("error")
    @JsonPropertyDescription("")
    @Valid
    private Error error;
    /**
     *
     */
    @JsonProperty("terms")
    private java.lang.String terms;
    /**
     *
     */
    @JsonProperty("privacy")
    private java.lang.String privacy;
    /**
     *
     */
    @JsonProperty("currencies")
    @Valid
    private Map<String, String> currencies;
    @JsonIgnore
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    /**
     * (Required)
     *
     * @return The success
     */
    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    /**
     * (Required)
     *
     * @param success The success
     */
    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * Error details
     *
     * @return The error
     */
    @JsonProperty("error")
    public Error getError() {
        return error;
    }

    /**
     * Error details
     *
     * @param error The error
     */
    @JsonProperty("error")
    public void setError(Error error) {
        this.error = error;
    }

    /**
     * @return The terms
     */
    @JsonProperty("terms")
    public java.lang.String getTerms() {
        return terms;
    }

    /**
     * @param terms The terms
     */
    @JsonProperty("terms")
    public void setTerms(java.lang.String terms) {
        this.terms = terms;
    }

    /**
     * @return The privacy
     */
    @JsonProperty("privacy")
    public java.lang.String getPrivacy() {
        return privacy;
    }

    /**
     * @param privacy The privacy
     */
    @JsonProperty("privacy")
    public void setPrivacy(java.lang.String privacy) {
        this.privacy = privacy;
    }

    /**
     * @return The currencies
     */
    @JsonProperty("currencies")
    public Map<String, String> getCurrencies() {
        return currencies;
    }

    /**
     * @param currencies The currencies
     */
    @JsonProperty("currencies")
    public void setCurrencies(Map<String, String> currencies) {
        this.currencies = currencies;
    }

    @Override
    public java.lang.String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(success).append(error).append(terms).append(privacy).append(currencies).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Currencies) == false) {
            return false;
        }
        Currencies rhs = ((Currencies) other);
        return new EqualsBuilder().append(success, rhs.success).append(error, rhs.error).append(terms, rhs.terms).append(privacy, rhs.privacy).append(currencies, rhs.currencies).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
