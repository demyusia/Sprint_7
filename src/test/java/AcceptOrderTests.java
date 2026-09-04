import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import model.OrderModel;
import org.junit.Test;

import java.util.Arrays;

import static data.CourierData.FIRSTNAME;
import static data.CourierData.LOGIN;
import static data.CourierData.PASSWORD;
import static data.OrderData.*;
import static data.OrderData.BLACK_COLOUR;
import static data.OrderData.COMMENT;
import static data.OrderData.DELIVERY_DATE;
import static data.OrderData.METRO_STATION;
import static data.OrderData.PHONE;
import static data.OrderData.RENT_TIME;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.createCourier;
import static steps.CourierSteps.loginCourier;
import static steps.OrderSteps.*;

public class AcceptOrderTests extends BaseAPITest{

    @Test
    @DisplayName("Accept order with correct id and courierId. Check status code and body")
    @Description("You can accept order with correct id and courierId. Status code 200")
    public void checkAcceptOrderWithCorrectData() {
        order = new OrderModel(FIRSTNAME, LASTNAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, Arrays.asList(BLACK_COLOUR));
        order.setTrack(createOrder(order).path("track"));
        Integer orderId = getOrderByTrackNumber(order.getTrack()).path("order.id");

        courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);
        Integer courierId = loginCourier(courier).path("id");

        acceptOrder(orderId, courierId)
                .then()
//                .log().all()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Accept order with wrong courier id. Check status code")
    @Description("You can't accept order with wrong courier id. Status code 404")
    public void checkAcceptOrderWithWrongCourierId() {
        order = new OrderModel(FIRSTNAME, LASTNAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, Arrays.asList(BLACK_COLOUR));
        order.setTrack(createOrder(order).path("track"));
        Integer orderId = getOrderByTrackNumber(order.getTrack()).path("order.id");

        acceptOrder(orderId, 121354633)
                .then()
//                .log().all()
                .statusCode(404)
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Test
    @DisplayName("Accept order without courier id. Check status code")
    @Description("You can't accept order without courier id. Status code 400")
    public void checkAcceptOrderWithWithoutCourierId() {
        order = new OrderModel(FIRSTNAME, LASTNAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, Arrays.asList(BLACK_COLOUR));
        order.setTrack(createOrder(order).path("track"));
        Integer orderId = getOrderByTrackNumber(order.getTrack()).path("order.id");

        acceptOrder(orderId, null)
                .then()
//                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Accept order with wrong order id. Check status code")
    @Description("You can't accept order with wrong order id. Status code 404")
    public void checkAcceptOrderWithWrongOrderId() {
        courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);
        Integer courierId = loginCourier(courier).path("id");

        acceptOrder(321353654, courierId)
                .then()
//                .log().all()
                .statusCode(404)
                .body("message", equalTo("Заказа с таким id не существует"));
    }


    //В документации API неправильно описан сценарий "Без id курьера возвращается ошибка 400".
    //Так как id курьера - pathParam, возвращаться будет 404 ошибка, а не 400
    //Проверять сценарий с отсутствующим id курьера не имеет смысла


}
