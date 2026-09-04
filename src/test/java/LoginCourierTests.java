import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import org.junit.Test;

import static data.CourierData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.createCourier;
import static steps.CourierSteps.loginCourier;

public class LoginCourierTests extends BaseAPITest {

    @Test
    @DisplayName("Login courier with correct data. Check status code and response body")
    @Description("You can login courier with correct data. Status code 200. Response body contains id")
    public void checkLoginCourierWithCorrectData() {
        courier = new CourierModel(LOGIN, PASSWORD);
        createCourier(courier);
//                .then()
//                .log().all();
        loginCourier(courier)
                .then()
//                .log().all()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Login courier that doesn't exit. Check status code")
    @Description("You can't login courier that doesn't exist. Status code 404")
    public void checkLoginCourierThatDoesNotExist() {
        courier = new CourierModel(LOGIN, PASSWORD);
        loginCourier(courier)
                .then()
//                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with wrong login. Check status code")
    @Description("You can't login courier with wrong login. Status code 404")
    public void checkLoginCourierWithWrongLogin() {
        courier = new CourierModel(LOGIN, PASSWORD);
        createCourier(courier);
//                .then()
//                .log().all();
        courier.setLogin("Somebody");
        loginCourier(courier)
                .then()
//                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with wrong password. Check status code")
    @Description("You can't login courier with wrong password. Status code 404")
    public void checkLoginCourierWithWrongPassword() {
        courier = new CourierModel(LOGIN, PASSWORD);
        createCourier(courier);
//                .then()
//                .log().all();
        courier.setPassword("242653");
        loginCourier(courier)
                .then()
//                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier without login. Check status code")
    @Description("You can't login without login. Status code 400")
    public void checkLoginCourierWithoutLogin() {
        courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);
//                .then()
//                .log().all();
        courier.setLogin(null);
        loginCourier(courier)
                .then()
//                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier without password. Check status code")
    @Description("You can't login without password. Status code 400")
    public void checkLoginCourierWithoutPassword() {
        courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);
//                .then()
//                .log().all();
        courier.setPassword(null);
        loginCourier(courier)
                .then()
//                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}