package org.openexchange.jms.activemq;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.JmsException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JmsTransactionalSendTest.class)
@SpringBootApplication
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test.properties")
public class JmsTransactionalSendTest extends JmsUtilsTest {
    @Test
    @Transactional
    public void transactionRollback() throws InterruptedException {
        Assert.assertNull(jmsTemplate.receive(TEST_JMS_QUEUE));
        try {
            failedToSend(jmsTemplate, TEST_JMS_QUEUE);
        } catch (JmsException ignored){
            TestTransaction.end();
        }
        Assert.assertNull(jmsTemplate.receive(TEST_JMS_QUEUE));
    }

    @Test
    @Transactional
    @Commit
    public void transactionCommit() throws InterruptedException {
        Assert.assertNull(jmsTemplate.receive(TEST_JMS_QUEUE));
        successToSend(jmsTemplate, TEST_JMS_QUEUE);
        TestTransaction.end();
        Assert.assertNotNull(jmsTemplate.receive(TEST_JMS_QUEUE));
        Assert.assertNotNull(jmsTemplate.receive(TEST_JMS_QUEUE));
        Assert.assertNull(jmsTemplate.receive(TEST_JMS_QUEUE));
    }
}
