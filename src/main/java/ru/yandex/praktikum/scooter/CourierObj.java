package ru.yandex.praktikum.scooter;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.yandex.praktikum.scooter.POJO.Courier;
import io.qameta.allure.Step;

public class CourierObj {
    private static final String CREATE_COURIER = "/api/v1/courier/";
    private static final String CREATE_COURIER_LOGIN = "/api/v1/courier/login";


    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_COURIER);
    }
    @Step("Авторизация курьера")
    public Response loginCourier(Courier courier) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_COURIER_LOGIN);
    }


}
