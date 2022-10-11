package nauvalatmaja.learning.route.http;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Integer customerId;
    private Integer fulfillerId;
    private List<OrderItemRequest> items;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemRequest {
        private String productCode;
        private Integer quantity;
    }
}
