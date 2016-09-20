package org.openexchange.jpa;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Sms implements Serializable {
    private static final long serialVersionUID = -4214775545721925949L;
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Integer code;
    @Column(nullable = false, unique = true)
    private String messageId;
    @Column(nullable = false)
    private String mobileOriginate;
    @Column(nullable = false)
    private String mobileTerminate;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date receiveTime;

    public Sms() {
    }

    public Sms(String messageId, String mobileOriginate, String mobileTerminate, String text) {
        this.messageId = messageId;
        this.mobileOriginate = mobileOriginate;
        this.mobileTerminate = mobileTerminate;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMobileOriginate() {
        return mobileOriginate;
    }

    public void setMobileOriginate(String mobileOriginate) {
        this.mobileOriginate = mobileOriginate;
    }

    public String getMobileTerminate() {
        return mobileTerminate;
    }

    public void setMobileTerminate(String mobileTerminate) {
        this.mobileTerminate = mobileTerminate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}
