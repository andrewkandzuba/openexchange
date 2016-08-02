package org.openexchange.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RefreshScope
public class Application {
    private final Environment environment;
    @Value("${configuration.properties.language:EN}")
    private String language;

    @Autowired
    public Application(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping("/")
    public String query(@RequestParam("q") String q) {
        return environment.getProperty(q);
    }

    @RequestMapping("/language")
    public String language() {
        return language;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
