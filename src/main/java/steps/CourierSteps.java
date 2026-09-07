package steps;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;

import static data.CourierData.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Create new courier")
    public static Response createCourier(CourierModel courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH)
                .then()
                .extract().response();
    }

    @Step("Login courier")
    public static Response loginCourier(CourierModel courier) {
        JsonObject json = new JsonObject();
        json.addProperty("login", courier.getLogin());
        json.addProperty("password", courier.getPassword());
        return given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .extract().response();
    }

    @Step("Delete courier")
    public static Response deleteCourier(Integer id) {
        if (id != null) {
            return given()
                    .pathParam("id", id)
                    .when()
                    .delete(COURIER_DELETE_PATH, id)
                    .then()
                    .extract().response();
        } else {
            return null;
        }

    }
}
