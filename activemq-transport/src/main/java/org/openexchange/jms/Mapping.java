package org.openexchange.jms;

import org.openexchange.protocol.Quote;
import org.openexchange.protocol.Sms;

import java.util.Collections;
import java.util.Map;

public class Mapping {
    public static final Map<Class, String> queues = Collections.unmodifiableMap(Map.of(
            Sms.class, "sms.outbound.queue",
            Quote.class, "quotes.queue"
    ));
}