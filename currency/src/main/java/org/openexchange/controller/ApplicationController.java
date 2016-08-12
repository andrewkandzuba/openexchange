package org.openexchange.controller;

import org.openexchange.domain.Currency;
import org.openexchange.domain.Rate;
import org.openexchange.service.CurrencyService;
import org.openexchange.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ApplicationController {
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private RateService rateService;

    @RequestMapping(path = "/currencies", method = RequestMethod.GET)
    public List<Currency> findAll() {
        return currencyService.findAll();
    }

    @RequestMapping(path = "/currencies/{code}", method = RequestMethod.GET)
    public Currency findByCode(@PathVariable String code) {
        Currency found = currencyService.findByCode(code);
        if(found == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return found;
    }

    @RequestMapping(path = "quotes/{sourceCode}/{targetCode}", method = RequestMethod.GET)
    public BigDecimal findQuote(@PathVariable String sourceCode, @PathVariable String targetCode){
        Currency source = currencyService.findByCode(sourceCode);
        if(source == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        Currency target = currencyService.findByCode(targetCode);
        if(target == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        Rate rate = rateService.findRate(source, target);
        if(rate == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return rate.getQuote();
    }
}
