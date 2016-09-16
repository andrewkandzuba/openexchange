package org.openexchange.jms;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
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
public class JmsTransactionalReceiveTest extends JmsUtilsTest {
    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();
    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    @Transactional
    public void readRollback() {
        Assert.assertNull(jmsTemplate.receiveAndConvert(TEST_QUOTE_QUEUE));
        successToSend(jmsTemplate, TEST_QUOTE_QUEUE);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        TestTransaction.start();
        try {
            failedRead(jmsTemplate, TEST_QUOTE_QUEUE);
            TestTransaction.flagForCommit();
        } catch (JmsException ignored){
            TestTransaction.flagForRollback();
        }
        TestTransaction.end();

        Assert.assertNotNull(jmsTemplate.receive(TEST_QUOTE_QUEUE));
        Assert.assertNotNull(jmsTemplate.receive(TEST_QUOTE_QUEUE));
        Assert.assertNull(jmsTemplate.receive(TEST_QUOTE_QUEUE));
    }
}
