package nauvalatmaja.learning.route.http;

import javax.ws.rs.core.MediaType;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

public class ExternalHttpRoute extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration().bindingMode(RestBindingMode.json);

        rest("/camel/order/new")
            .consumes(MediaType.APPLICATION_JSON)
            .post()
            .type(OrderRequest.class)
            .produces(MediaType.APPLICATION_JSON)
            .to("direct:callExternal");

        from("direct:callExternal")
            .process(new Processor() {
                public void process(Exchange exchange) {
                    OrderRequest in = exchange.getIn().getBody(OrderRequest.class);
                    ExternalOrderRequest extRequest = ExternalOrderRequest.from(in);
                    exchange.getIn().setBody(extRequest);
                }
            })
            .removeHeader(Exchange.HTTP_PATH)
            .marshal().json(JsonLibrary.Jackson)
            .to("http://localhost:9110/external/order/new?httpMethod=POST&bridgeEndpoint=true");
    }
}
