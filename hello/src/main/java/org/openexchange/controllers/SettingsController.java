package org.openexchange.controllers;

import org.openexchange.config.HelloConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingsController {
    private final Environment environment;
    private final HelloConfiguration helloConfiguration;

    @Autowired
    public SettingsController(Environment environment, HelloConfiguration helloConfiguration) {
        this.environment = environment;
        this.helloConfiguration = helloConfiguration;
    }

    @RequestMapping("/")
    public String query(@RequestParam("q") String q) {
        return environment.getProperty(q);
    }

    @RequestMapping("/language")
    public String language() {
        return helloConfiguration.getLanguage();
    }
}
