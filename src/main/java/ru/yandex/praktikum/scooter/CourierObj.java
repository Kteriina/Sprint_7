package ru.yandex.praktikum.scooter;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.yandex.praktikum.scooter.pojo.Courier;
import io.qameta.allure.Step;

public class CourierObj {
    private static final String CREATE_COURIER = "/api/v1/courier/";
    private static final String CREATE_COURIER_LOGIN = "/api/v1/courier/login";
    private static final String DELETE_COURIER = "/api/v1/courier/{id}";


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
    @Step("Получение id курьера")
    public String getCourierId(Courier courier) {
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_COURIER_LOGIN);
        return response.jsonPath().getString("id");

    }

    @Step("Удаление курьера")
    public Response deleteCourier(String idCourier) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .when()
                .delete(DELETE_COURIER, idCourier);
    }




}
