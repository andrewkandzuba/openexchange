package org.openexchange.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openexchange.pojos.Sms;
import org.openexchange.sms.SmsService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsServiceMonitoringTest.class)
@SpringBootApplication
public class SmsServiceMonitoringTest {
    @MockBean
    private SmsService smsService;

    @Test
    public void send() throws Exception {
        smsService.send(Collections.singleton(
                new Sms()
                        .withMessageId(UUID.randomUUID())
                        .withMobileOriginate("+37258000000")
                        .withMobileTerminate("+37258111111")
                        .withText("Test")
                        .withReceiveTime(Date.from(Instant.now()))));
    }

    @Test(expected = NullPointerException.class)
    public void sendingFails() throws Exception {
        smsService.send(Collections.singleton(
                new Sms()
                        .withMessageId(UUID.randomUUID())
                        .withMobileOriginate("+37258000000")
                        .withText("Test")
                        .withReceiveTime(Date.from(Instant.now()))));
    }

    @Test
    public void received() throws Exception {
        Mockito.when(smsService.receive(1)).thenReturn(
                Collections.singleton(
                        new Sms()
                                .withMessageId(UUID.randomUUID())
                                .withMobileOriginate("+37258000000")
                                .withMobileTerminate("+37258111111")
                                .withText("Test")
                                .withReceiveTime(Date.from(Instant.now()))));
        smsService.receive(1);
    }

    @Test(expected = NullPointerException.class)
    public void receiptFails() throws Exception {
        Mockito.when(smsService.receive(1)).thenReturn(
                Collections.singleton(
                        new Sms()
                                .withMessageId(UUID.randomUUID())
                                .withMobileOriginate("+37258000000")
                                .withText("Test")
                                .withReceiveTime(Date.from(Instant.now()))));
        smsService.receive(1);
    }
}
