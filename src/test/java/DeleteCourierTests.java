import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.*;

public class DeleteCourierTests extends BaseAPITest {

    @Test
    @DisplayName("Delete courier with correct id. Check status code and response body")
    @Description("You can delete courier using correct id. Status code 200. Body contains 'ok:true'")
    public void checkDeleteCourierWithCorrectId() {
        createCourier(courier);
        int id = loginCourier(courier).path("id");
        deleteCourier(id)
                .then()
                .statusCode(SC_OK)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Delete courier with non-existent id. Check status code")
    @Description("You can't delete courier with non-existent id. Status code 404")
    public void checkDeleteCourierWithNonExistentId() {
        deleteCourier(1654654132)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id нет"));
    }

    //В документации API неправильно описан сценарий "Без id курьера возвращается ошибка 400".
    //API игнорирует данные переданные в body запроса, удаление осуществляется по id курьера, переданному в url
    //Так как id курьера - pathParam, возвращаться будет 404 ошибка, а не 400
    //Проверять сценарий с отсутствующим id курьера не имеет смысла
}
