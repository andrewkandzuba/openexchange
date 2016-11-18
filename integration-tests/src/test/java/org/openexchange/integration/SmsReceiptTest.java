package org.openexchange.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = {
        "org.openexchange.repository",
        "org.openexchange.transactions"
})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test.properties")
//@ActiveProfiles("integration-test")
public class SmsReceiptTest {
    //@Inject
    //private SmsRepository smsRepository;

    @Test
    public void smsReceipt() throws Exception {
    //    Assert.assertTrue(smsRepository.count() > 0);
    }
}
