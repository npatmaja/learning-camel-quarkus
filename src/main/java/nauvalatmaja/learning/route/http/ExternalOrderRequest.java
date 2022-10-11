package nauvalatmaja.learning.route.http;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalOrderRequest{
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


