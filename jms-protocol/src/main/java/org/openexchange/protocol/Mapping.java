package org.openexchange.protocol;

import java.util.Collections;
import java.util.Map;

public class Mapping {
    public static final Map<Class, String> queues = Collections.unmodifiableMap(Map.of(
            Sms.class, "sms.outbound.queue",
            Quote.class, "quotes.queue"
    ));
}