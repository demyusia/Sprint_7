import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.OrderModel;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;

import static data.OrderData.*;
import static data.OrderData.COMMENT;
import static data.OrderData.DELIVERY_DATE;
import static data.OrderData.METRO_STATION;
import static data.OrderData.PHONE;
import static data.OrderData.RENT_TIME;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.createOrder;
import static steps.OrderSteps.getOrderByTrackNumber;

public class GetOrderByTrackNumberTests extends BaseAPITest{

    @Test
    @DisplayName("Get order by correct track number. Check status code and body")
    @Description("You can get order by correct track number. Status code 200")
    public void checkGettingOrderByCorrectTrackNumber() {
        order = new OrderModel(FIRSTNAME, LASTNAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, Arrays.asList(BLACK_COLOUR));
        order.setTrack(createOrder(order).path("track"));
        getOrderByTrackNumber(order.getTrack())
                .then()
//                .log().all()
                .statusCode(200)
                .body("order.id", notNullValue())
                .body("order.firstName", equalTo(order.getFirstName()))
                .body("order.lastName", equalTo(order.getLastName()))
                .body("order.address", equalTo(order.getAddress()))
                .body("order.metroStation", equalTo(order.getMetroStation()))
                .body("order.phone", equalTo(order.getPhone()))
                .body("order.rentTime", equalTo(order.getRentTime()))
                .body("order.deliveryDate", Matchers.startsWith(order.getDeliveryDate()))
                .body("order.track", equalTo(order.getTrack()))
                .body("order.color", equalTo(order.getColor()))
                .body("order.comment", equalTo(order.getComment()))
                .body("order.cancelled", notNullValue())
                .body("order.finished", notNullValue())
                .body("order.inDelivery", notNullValue())
                .body("order.courierFirstName", notNullValue())
                .body("order.createdAt", notNullValue())
                .body("order.updatedAt", notNullValue())
                .body("order.status", notNullValue());
    }

    @Test
    @DisplayName("Get order with wrong track number. Check status code")
    @Description("You can't get order with wrong track number. Status code 404")
    public void checkGettingOrderWithWrongTrackNumber() {
        getOrderByTrackNumber(536312130)
                .then()
//                .log().all()
                .statusCode(404)
                .body("message", equalTo("Заказ не найден"));
    }

    @Test
    @DisplayName("Get order without track number. Check status code")
    @Description("You can't get order without track number. Status code 400")
    public void checkGettingOrderWithoutTrackNumber() {
        getOrderByTrackNumber(null)
                .then()
//                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }
}
