package steps;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static data.OrderData.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Create order")
    public static Response createOrder(OrderModel order) {
        return given()
//                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Cancel order")
    public static Response cancelOrder (int track) {
        JsonObject json = new JsonObject();
        json.addProperty("track", track);
        if (track != 0) {
            return given()
                    .contentType(ContentType.JSON)
                    .body(json)
                    .when()
                    .put(CANCEL_ORDER_PATH)
                    .then()
                    .extract().response();
        } else {
            return null;
        }
    }

    @Step("Get order by track number")
    public static Response getOrderByTrackNumber(Integer track) {
        return given()
//                .log().all()
                .queryParam("t", track)
                .get(GET_ORDER_BY_TRACK)
                .then()
                .extract().response();
    }

    @Step("Accept order")
    public static Response acceptOrder(Integer id, Integer courierId) {
        return given()
//                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .queryParam("courierId", courierId)
                .when()
                .put(ACCEPT_ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Get list of orders")
    public static Response getOrderList() {
        return given()
//                .log().all()
                .contentType(ContentType.JSON)
                .get(GET_ORDER_LIST)
                .then()
                .extract().response();
    }
}
