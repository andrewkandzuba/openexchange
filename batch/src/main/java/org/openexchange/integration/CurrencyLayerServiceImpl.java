package org.openexchange.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CurrencyLayerServiceImpl implements CurrencyLayerService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyLayerServiceImpl.class.getName());
    @Autowired
    private RestTemplate restTemplate;
    @Value("${currencylayer.api.accesskey}")
    private String accessKey;
    @Value("${currencylayer.api.endpoint}")
    private String endpoint;

    public Currencies all() {
        logger.info("Retrieves value from an external service");
        ResponseEntity<Currencies> resp = restTemplate.getForEntity(
                endpoint + "/api/list?access_key={access_key}&format={format}",
                Currencies.class,
                accessKey, 1);
        logger.info("The response status is " + resp.getStatusCode());
        if (!resp.getStatusCode().equals(HttpStatus.OK)) {
            throw new IllegalStateException("Unable to retrieve the list of currencies! Response details: " + resp.toString());
        }
        return resp.getBody();
    }

    @Override
    public Quotes live(List<String> currencyCodes) {
        logger.info("Retrieves value from an external service");
        ResponseEntity<Quotes> resp = restTemplate.getForEntity(
                endpoint + "/api/live?access_key={access_key}&currencies={currenciesCodes}&format={format}",
                Quotes.class,
                accessKey,
                StringUtils.arrayToCommaDelimitedString(currencyCodes.toArray()), 1);
        logger.info("The response status is " + resp.getStatusCode());
        if (!resp.getStatusCode().equals(HttpStatus.OK)) {
            throw new IllegalStateException("Unable to retrieve the list of quotes! Response details: " + resp.toString());
        }
        return resp.getBody();
    }
}
