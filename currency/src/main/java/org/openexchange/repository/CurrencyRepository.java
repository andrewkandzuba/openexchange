package org.openexchange.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {
}
