package nauvalatmaja.learning.route.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nauvalatmaja.learning.route.http.OrderRequest.OrderItemRequest;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalOrderItemRequest {
    private String product;
    private Integer quantity;

    public static ExternalOrderItemRequest from(OrderItemRequest item) {
        return ExternalOrderItemRequest.builder()
            .product(item.getProductCode())
            .quantity(item.getQuantity())
            .build();
    }
}
