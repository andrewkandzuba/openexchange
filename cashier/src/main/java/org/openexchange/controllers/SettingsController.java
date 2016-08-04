package org.openexchange.controllers;

import org.openexchange.config.CashierConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingsController {
    private final Environment environment;
    private final CashierConfiguration cashierConfiguration;

    @Autowired
    public SettingsController(Environment environment, CashierConfiguration cashierConfiguration) {
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
}
