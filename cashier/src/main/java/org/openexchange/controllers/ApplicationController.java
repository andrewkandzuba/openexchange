package org.openexchange.controllers;

import org.openexchange.config.CashierConfiguration;
import org.openexchange.service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class ApplicationController {
    private final Environment environment;
    private final CashierConfiguration cashierConfiguration;
    private final CashierService cashierService;

    @Autowired
    public ApplicationController(Environment environment, CashierConfiguration cashierConfiguration, CashierService cashierService) {
        this.environment = environment;
        this.cashierConfiguration = cashierConfiguration;
        this.cashierService = cashierService;
    }

    @RequestMapping("/")
    public String query(@RequestParam("q") String q) {
        return environment.getProperty(q);
    }

    @RequestMapping("/language")
    public String language() {
        return cashierConfiguration.getLanguage();
    }

    @RequestMapping("/currency")
    public String currency() {
        return cashierConfiguration.getCurrency();
    }

    @RequestMapping("/exchange/{source}/{target}/{amount}")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BigDecimal exchange(@PathVariable String source, @PathVariable String target, @PathVariable BigDecimal amount){
        return cashierService.exchange(source, target, amount);
    }
}
