import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.createCourier;
import static steps.CourierSteps.loginCourier;
import static steps.OrderSteps.*;

public class AcceptOrderTests extends BaseAPITest{

    @Test
    @DisplayName("Accept order with correct id and courierId. Check status code and body")
    @Description("You can accept order with correct id and courierId. Status code 200")
    public void checkAcceptOrderWithCorrectData() {
        order.setTrack(createOrder(order).path("track"));
        Integer orderId = getOrderByTrackNumber(order.getTrack()).path("order.id");

        createCourier(courier);
        Integer courierId = loginCourier(courier).path("id");

        acceptOrder(orderId, courierId)
                .then()
                .statusCode(SC_OK)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Accept order with wrong courier id. Check status code")
    @Description("You can't accept order with wrong courier id. Status code 404")
    public void checkAcceptOrderWithWrongCourierId() {
        order.setTrack(createOrder(order).path("track"));
        Integer orderId = getOrderByTrackNumber(order.getTrack()).path("order.id");

        acceptOrder(orderId, 121354633)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Test
    @DisplayName("Accept order without courier id. Check status code")
    @Description("You can't accept order without courier id. Status code 400")
    public void checkAcceptOrderWithWithoutCourierId() {
        order.setTrack(createOrder(order).path("track"));
        Integer orderId = getOrderByTrackNumber(order.getTrack()).path("order.id");

        acceptOrder(orderId, null)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Accept order with wrong order id. Check status code")
    @Description("You can't accept order with wrong order id. Status code 404")
    public void checkAcceptOrderWithWrongOrderId() {
        createCourier(courier);
        Integer courierId = loginCourier(courier).path("id");

        acceptOrder(321353654, courierId)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Заказа с таким id не существует"));
    }


    //В документации API неправильно описан сценарий "Без id курьера возвращается ошибка 400".
    //Так как id курьера - pathParam, возвращаться будет 404 ошибка, а не 400
    //Проверять сценарий с отсутствующим id курьера не имеет смысла


}
