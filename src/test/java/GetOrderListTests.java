import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.getOrderList;

public class GetOrderListTests extends BaseAPITest{

    @Test
    @DisplayName("Get list of orders.Check status code and body")
    @Description("You can get list of orders. Status code 200")
    public void checkGettingOrderList() {
        getOrderList()
                .then()
                .statusCode(SC_OK)
                .body("orders", notNullValue())
                .body("pageInfo", notNullValue())
                .body("availableStations", notNullValue());

    }
}
