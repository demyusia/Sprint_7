import io.restassured.RestAssured;
import model.CourierModel;
import model.OrderModel;
import org.junit.After;
import org.junit.Before;

import java.util.Arrays;

import static data.CourierData.*;
import static data.OrderData.*;

import static steps.CourierSteps.deleteCourier;
import static steps.CourierSteps.loginCourier;
import static steps.OrderSteps.cancelOrder;

public class BaseAPITest {

    protected CourierModel courier;
    protected OrderModel order;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courier = new CourierModel(LOGIN, PASSWORD, COURIER_FIRSTNAME);
        order = new OrderModel(CLIENT_FIRSTNAME, CLIENT_LASTNAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, Arrays.asList(BLACK_COLOUR));
    }

    @After
    public void cleanUp() throws Exception {
        if (courier != null) {
            try {
                int idCourier = loginCourier(courier).path("id");
                deleteCourier(idCourier);
            } catch (Throwable t) {
            }
        }
        if (order != null) {
            try {
                cancelOrder(order.getTrack());
            } catch (Throwable t) {
            }
        }
    }
}
