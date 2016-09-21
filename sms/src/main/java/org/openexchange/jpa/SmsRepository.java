package org.openexchange.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = "smsTransactionManager")
public interface SmsRepository extends JpaRepository<Sms, Integer> {
}
