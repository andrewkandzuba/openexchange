package org.openexchange.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;

@FeignClient("currency")
public interface CurrencyClient {
    @RequestMapping(path = "rates/{sourceCode}/{targetCode}", method = RequestMethod.GET)
    BigDecimal findRate(@PathVariable String sourceCode, @PathVariable String targetCode);
}
