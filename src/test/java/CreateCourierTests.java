import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;

public class CreateCourierTests extends BaseAPITest {

    @Test
    @DisplayName("Create new courier and check status code test")
    @Description("Create new courier using correct name and password. Status code must be 201 'ok: true'")
    public void createCourierWithCorrectData() {
        createCourier(courier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Create new courier with ununique login. Check status code")
    @Description("You can't create new courier with ununique login. Status code 409")
    public void checkCreateCourierWithUnuniqueLogin () {
        createCourier(courier);
        createCourier(courier)
                .then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Create courier without login. Check status code")
    @Description("You can't create courier without login. Status code 400")
    public void checkCreateCourierWithoutLogin() {
        String temp = courier.getLogin();
        courier.setLogin(null);
        createCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        courier.setLogin(temp);
    }

    @Test
    @DisplayName("Create courier without password. Check status code")
    @Description("You can't create courier without password. Status code 400")
    public void checkCreateCourierWithoutPassword() {
        String temp = courier.getPassword();
        courier.setPassword(null);
        createCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        courier.setPassword(temp);
    }

    @Test
    @DisplayName("Create courier without firstname. Check status code")
    @Description("Firstname is optional. Creating courier without password is possible. Status code 201")
    public void checkCreateCourierWithoutFirstname() {
        courier.setFirstName(null);
        createCourier(courier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }
}
