package nauvalatmaja.learning;

import javax.enterprise.inject.Produces;

import org.apache.camel.Exchange;
import org.apache.camel.spi.ExchangeFormatter;

public class ExchangeFormatterProducer {

    enum LogColor {
        BLUE("\u001b[34m"),
        YELLOW("\u001b[33m");

        private String escapeCode;

        LogColor(String escapeCode) {
            this.escapeCode = escapeCode;
        }

        public String apply(String string) {
            return this.escapeCode + string + "\u001b[0m";
        }
    }

    @Produces
    public ExchangeFormatter exchangeFormatter() {
        return new ExchangeFormatter() {
            @Override
            public String format(Exchange exchange) {
                String toEndpoint = exchange.getProperty(Exchange.TO_ENDPOINT, String.class);
                String body = exchange.getMessage().getBody(String.class);
                switch (toEndpoint) {
                    case "log://timer":
                        return LogColor.BLUE.apply("Java DSL: " + body);
                    case "log://timer-xml":
                        return LogColor.YELLOW.apply("XML DSL: " + body);
                    default:
                        return body;
                }
            }
        };
    }
}
