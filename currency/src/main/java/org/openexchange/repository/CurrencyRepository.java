package org.openexchange.repository;

import org.openexchange.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
