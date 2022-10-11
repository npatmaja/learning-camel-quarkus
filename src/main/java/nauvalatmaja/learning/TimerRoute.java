package nauvalatmaja.learning;

import org.apache.camel.builder.RouteBuilder;

public class TimerRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("timer:foo?period=2s")
            .bean("greeting", "greet")
            .to("log:timer");

        from("timer:from-xml?period=3s")
            .bean("greeting", "greet")
            .to("log:timer-xml");
    }
}
