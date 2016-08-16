package org.openexchange.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Rate implements Serializable {
    private static final long serialVersionUID = 5160208533569040830L;

    @EmbeddedId
    private RatePK id;
    @Column(precision = 10, scale = 6, nullable = false)
    private BigDecimal quote;

    public Rate() {
    }

    public Rate(Currency source, Currency target, BigDecimal quote) {
        this.id = new RatePK(source, target);
        this.quote = quote;
    }

    public Currency getSource(){
        return id.getSource();
    }

    void setSource(Currency source){
        id.setSource(source);
    }

    public Currency getTarget(){
        return id.getTarget();
    }

    void setTarget(Currency target){
        id.setTarget(target);
    }

    public BigDecimal getQuote() {
        return quote;
    }

    public void setQuote(BigDecimal quote) {
        this.quote = quote;
    }

    @Embeddable
    public static class RatePK implements Serializable {
        private static final long serialVersionUID = 6663463117714621191L;

        @OneToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(referencedColumnName = "code")
        private Currency source;
        @OneToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(referencedColumnName = "code")
        private Currency target;

        public RatePK() {
        }

        public RatePK(Currency source, Currency target) {
            this.source = source;
            this.target = target;
        }

        public boolean equals(Object object) {
            if (object instanceof RatePK) {
                RatePK pk = (RatePK) object;
                return source.equals(pk.source) && target.equals(pk.target);
            } else {
                return false;
            }
        }

        public int hashCode() {
            return source.hashCode() + target.hashCode();
        }

        Currency getSource() {
            return source;
        }

        void setSource(Currency source) {
            this.source = source;
        }

        Currency getTarget() {
            return target;
        }

        void setTarget(Currency target) {
            this.target = target;
        }
    }
}
