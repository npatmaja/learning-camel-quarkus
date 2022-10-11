package nauvalatmaja.learning.route.http;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nauvalatmaja.learning.route.http.OrderRequest.OrderItemRequest;


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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ExternalOrderRequest{
        @JsonProperty("customer-id")
        private Integer customerId;
        @JsonProperty("distributor-id")
        private Integer distributorId;
        private List<ExternalOrderItemRequest> items;

        public static ExternalOrderRequest from(OrderRequest order) {
            return ExternalOrderRequest.builder()
                .customerId(order.getCustomerId())
                .distributorId(order.getFulfillerId())
                .items(order.getItems().stream().map(ExternalOrderItemRequest::from).collect(Collectors.toList()))
                .build();
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ExternalOrderItemRequest {
        private String product;
        private Integer quantity;

        public static ExternalOrderItemRequest from(OrderItemRequest item) {
            return ExternalOrderItemRequest.builder()
                .product(item.getProductCode())
                .quantity(item.getQuantity())
                .build();
        }
    }
}
