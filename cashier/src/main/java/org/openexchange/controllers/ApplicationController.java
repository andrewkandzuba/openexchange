package org.openexchange.controllers;

import org.openexchange.config.CashierConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ApplicationController {
    private final Environment environment;
    private final CashierConfiguration cashierConfiguration;

    @Autowired
    public ApplicationController(Environment environment, CashierConfiguration cashierConfiguration) {
        this.environment = environment;
        this.cashierConfiguration = cashierConfiguration;
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
    public BigDecimal exchange(@PathVariable String source, @PathVariable String target, @PathVariable BigDecimal amount){
        return new BigDecimal(0);
    }
}
