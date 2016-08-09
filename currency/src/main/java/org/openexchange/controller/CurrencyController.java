package org.openexchange.controller;

import org.openexchange.domain.Currency;
import org.openexchange.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(path = "/currencies", method = RequestMethod.GET)
    public List<Currency> findAll() {
        return currencyService.findAll();
    }

    @RequestMapping(path = "/currencies/{code}", method = RequestMethod.GET)
    public Currency findByCode(@PathVariable String code) {
        Currency found =  currencyService.findByCode(code);
        if(found == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return found;
    }
}
