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
 * List of currency quotes
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "success",
    "error",
    "terms",
    "privacy",
    "historical",
    "date",
    "timestamp",
    "source",
    "quotes"
})
public class Quotes {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("success")
    private Boolean success;
    /**
     * Error details
     * 
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
    @JsonProperty("historical")
    private Boolean historical = false;
    /**
     * 
     */
    @JsonProperty("date")
    private java.lang.String date;
    /**
     * 
     */
    @JsonProperty("timestamp")
    private Long timestamp;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("source")
    private java.lang.String source;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("quotes")
    @Valid
    private Map<String, Double> quotes;
    @JsonIgnore
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The success
     */
    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * (Required)
     * 
     * @param success
     *     The success
     */
    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * Error details
     * 
     * @return
     *     The error
     */
    @JsonProperty("error")
    public Error getError() {
        return error;
    }

    /**
     * Error details
     * 
     * @param error
     *     The error
     */
    @JsonProperty("error")
    public void setError(Error error) {
        this.error = error;
    }

    /**
     * 
     * @return
     *     The terms
     */
    @JsonProperty("terms")
    public java.lang.String getTerms() {
        return terms;
    }

    /**
     * 
     * @param terms
     *     The terms
     */
    @JsonProperty("terms")
    public void setTerms(java.lang.String terms) {
        this.terms = terms;
    }

    /**
     * 
     * @return
     *     The privacy
     */
    @JsonProperty("privacy")
    public java.lang.String getPrivacy() {
        return privacy;
    }

    /**
     * 
     * @param privacy
     *     The privacy
     */
    @JsonProperty("privacy")
    public void setPrivacy(java.lang.String privacy) {
        this.privacy = privacy;
    }

    /**
     * 
     * @return
     *     The historical
     */
    @JsonProperty("historical")
    public Boolean getHistorical() {
        return historical;
    }

    /**
     * 
     * @param historical
     *     The historical
     */
    @JsonProperty("historical")
    public void setHistorical(Boolean historical) {
        this.historical = historical;
    }

    /**
     * 
     * @return
     *     The date
     */
    @JsonProperty("date")
    public java.lang.String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    @JsonProperty("date")
    public void setDate(java.lang.String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * @param timestamp
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The source
     */
    @JsonProperty("source")
    public java.lang.String getSource() {
        return source;
    }

    /**
     * 
     * (Required)
     * 
     * @param source
     *     The source
     */
    @JsonProperty("source")
    public void setSource(java.lang.String source) {
        this.source = source;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The quotes
     */
    @JsonProperty("quotes")
    public Map<String, Double> getQuotes() {
        return quotes;
    }

    /**
     * 
     * (Required)
     * 
     * @param quotes
     *     The quotes
     */
    @JsonProperty("quotes")
    public void setQuotes(Map<String, Double> quotes) {
        this.quotes = quotes;
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
        return new HashCodeBuilder().append(success).append(error).append(terms).append(privacy).append(historical).append(date).append(timestamp).append(source).append(quotes).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Quotes) == false) {
            return false;
        }
        Quotes rhs = ((Quotes) other);
        return new EqualsBuilder().append(success, rhs.success).append(error, rhs.error).append(terms, rhs.terms).append(privacy, rhs.privacy).append(historical, rhs.historical).append(date, rhs.date).append(timestamp, rhs.timestamp).append(source, rhs.source).append(quotes, rhs.quotes).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
