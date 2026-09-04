import io.restassured.RestAssured;
import model.CourierModel;
import model.OrderModel;
import org.junit.After;
import org.junit.Before;

import static steps.CourierSteps.deleteCourier;
import static steps.CourierSteps.loginCourier;
import static steps.OrderSteps.cancelOrder;

public class BaseAPITest {

    protected CourierModel courier;
    protected OrderModel order;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courier = null;
        order = null;
    }

    @After
    public void cleanUp() throws Exception {
        if (courier != null) {
            try {
                int idCourier = loginCourier(courier).path("id");
                deleteCourier(idCourier);
            } catch (RuntimeException e) {
            }
        }
        if (order != null) {
            try {
                cancelOrder(order.getTrack());
            } catch (RuntimeException e) {
            }
        }
    }
}
