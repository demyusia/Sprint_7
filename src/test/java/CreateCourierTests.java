import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import org.junit.Test;

import static data.CourierData.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;

public class CreateCourierTests extends BaseAPITest {

    @Test
    @DisplayName("Create new courier and check status code test")
    @Description("Create new courier using correct name and password. Status code must be 201 'ok: true'")
    public void createNewCourierAndCheckStatusCode() {
        courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier)
                .then()
//                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Create new courier with ununique login. Check status code")
    @Description("You can't create new courier with ununique login. Status code 409")
    public void checkStatusCodeWhenTryingToCreateCourierWithUnuniqueLogin () {
        courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);
//                .then()
//                .log().all();
        createCourier(courier)
                .then()
//                .log().all()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Create courier without login. Check status code")
    @Description("You can't create courier without login. Status code 400")
    public void checkStatusCodeWhenTryingToCreateCourierWithoutLogin() {
        courier = new CourierModel(null, PASSWORD, FIRSTNAME);
        createCourier(courier)
                .then()
//                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without password. Check status code")
    @Description("You can't create courier without password. Status code 400")
    public void checkStatusCodeWhenTryingToCreateCourierWithoutPassword() {
        courier = new CourierModel(LOGIN, null, FIRSTNAME);
        createCourier(courier)
                .then()
//                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without firstname. Check status code")
    @Description("Firstname is optional. Creating courier without password is possible. Status code 201")
    public void checkStatusCodeWhenTryingToCreateCourierWithoutFirstname() {
        courier = new CourierModel(LOGIN, PASSWORD, null);
        createCourier(courier)
                .then()
//                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));
    }
}
