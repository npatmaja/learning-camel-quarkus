package nauvalatmaja.learning;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SimpleHTTPRouteIT {
    private static ExternalRestMockServer mockServer = new ExternalRestMockServer();

    @BeforeAll
    static void beforeEach() {
        mockServer.start();
    }

    @AfterAll
    static void afterAll() {
        mockServer.stop();
    }

    @Test
    void givenValidRequest_whenConsume_shouldCallToCorrectAPI() {
        given()
            .body("{\"customerId\":123,\"fulfillerId\":888,\"items\":[{\"productCode\":\"A101010\",\"quantity\":9},{\"productCode\":\"A909090\",\"quantity\":2}]}")
            .contentType(ContentType.JSON)
            .when().post("/camel/order/new")
            .then()
            .statusCode(201);
        mockServer.verifyRequestReceiveAtLeastOnce();
    }
}   
