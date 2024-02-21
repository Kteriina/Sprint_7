package ru.yandex.praktikum.scooter;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.yandex.praktikum.scooter.pojo.Order;

public class OrderObj {
    private static final String GET_ORDERS = "/api/v1/orders";
    private static final String CREATE_ORDER = "/api/v1/orders";
    private static final String CANCEL_ORDER = "/api/v1/orders/cancel";

    @Step("Получение списка всех заказов")
    public Response getListOrders() {
        return RestAssured.given()
                .get(GET_ORDERS);
    }

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER);
    }

    @Step("Отмена заказа")
    public Response cancelOrder(int trackOrder) {
        return RestAssured. given()
                .header("Content-type", "application/json")
                .body("{\"track\":" + trackOrder + "}")
                .when()
                .put(CANCEL_ORDER);
    }
}
