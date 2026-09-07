import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static data.OrderData.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class CreateOrderTests extends BaseAPITest{

    @Parameterized.Parameter
    public List<String> colourScooter;

    @Parameterized.Parameters(name = "Тестовые данные: цвет={0}")
    public static Object[][] getData() {
        return new Object[][] {
                {Arrays.asList(BLACK_COLOUR)},
                {Arrays.asList(GREY_COLOUR)},
                {Arrays.asList(BLACK_COLOUR, GREY_COLOUR)},
                {null},
        };
    }

    @Test
    @DisplayName("Create an order with different colours of scooter.Check status code and track number")
    @Description("When you create an order, you can choose: grey, black, grey&black or non colour. Status code 201. Body contains track number")
    public void checkCreateOrderWithDifferentColours() {
        order.setColor(colourScooter);
        Response response = createOrder(order);
        response
                .then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
        order.setTrack(response.path("track"));
    }

}
