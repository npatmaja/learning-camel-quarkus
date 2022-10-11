package nauvalatmaja.learning;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.MatchType;
import org.mockserver.verify.VerificationTimes;
import org.mockserver.client.MockServerClient;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

public class ExternalRestMockServer {
    private ClientAndServer mockServer;

    public void start() {
        mockServer = startClientAndServer(9110);
        createExpectationCreateOrder();
    }

    public void stop() {
        mockServer.stop();
    }

    public void createExpectationCreateOrder() {
        new MockServerClient("localhost", 9110)
            .when(
                request()
                    .withMethod("POST")
                    .withPath("/external/order/new")
                    .withBody(json(
                            "{\"customer-id\":123,\"distributor-id\":888,\"items\":[{\"product\":\"A101010\",\"quantity\":9},{\"product\":\"A909090\",\"quantity\":2}]}",
                            MatchType.ONLY_MATCHING_FIELDS))
                )
            .respond(
                response()
                    .withStatusCode(201)
                    .withBody("{\"order-id\":90900}")
                );
    }
    
    public void verifyRequestReceiveAtLeastOnce() {
        new MockServerClient("localhost", 9110)
            .verify(
                    request()
                    .withMethod("POST")
                        .withPath("/external/order/new"),
                    VerificationTimes.once()
                );
    } 
}
