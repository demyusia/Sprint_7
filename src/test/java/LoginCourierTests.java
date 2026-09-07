import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.createCourier;
import static steps.CourierSteps.loginCourier;

public class LoginCourierTests extends BaseAPITest {

    @Test
    @DisplayName("Login courier with correct data. Check status code and response body")
    @Description("You can login courier with correct data. Status code 200. Response body contains id")
    public void checkLoginCourierWithCorrectData() {
        createCourier(courier);
        loginCourier(courier)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Login courier that doesn't exit. Check status code")
    @Description("You can't login courier that doesn't exist. Status code 404")
    public void checkLoginCourierThatDoesNotExist() {
        loginCourier(courier)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with wrong login. Check status code")
    @Description("You can't login courier with wrong login. Status code 404")
    public void checkLoginCourierWithWrongLogin() {
        createCourier(courier);
        String temp = courier.getLogin();
        courier.setLogin("Somebody");
        loginCourier(courier)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
        courier.setLogin(temp);
    }

    @Test
    @DisplayName("Login courier with wrong password. Check status code")
    @Description("You can't login courier with wrong password. Status code 404")
    public void checkLoginCourierWithWrongPassword() {
        createCourier(courier);
        String temp = courier.getPassword();
        courier.setPassword("242653");
        loginCourier(courier)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
        courier.setPassword(temp);
    }

    @Test
    @DisplayName("Login courier without login. Check status code")
    @Description("You can't login without login. Status code 400")
    public void checkLoginCourierWithoutLogin() {
        createCourier(courier);
        String temp = courier.getLogin();
        courier.setLogin(null);
        loginCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
        courier.setLogin(temp);
    }

    @Test
    @DisplayName("Login courier without password. Check status code")
    @Description("You can't login without password. Status code 400")
    public void checkLoginCourierWithoutPassword() {
        createCourier(courier);
        String temp = courier.getPassword();
        courier.setPassword(null);
        loginCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
        courier.setPassword(temp);
    }
}