package org.openexchange.transactions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openexchange.CurrencyApplication;
import org.openexchange.repository.CurrencyRepository;
import org.openexchange.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CurrencyApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test.properties")
public class TransactionalServiceTest {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private TransactionalService transactionalService;

    @Test
    public void testRollbackAll() throws Exception {
        try {
            transactionalService.addAndRollbackAllOnException();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertEquals(0, currencyRepository.findAll().size());
            Assert.assertEquals(0, rateRepository.findAll().size());
        }
    }

    @Test
    public void testCommitAll() throws Exception {
        transactionalService.addAndCommitAll();
        Assert.assertEquals(2, currencyRepository.findAll().size());
        Assert.assertEquals(1, rateRepository.findAll().size());
    }
}
