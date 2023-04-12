package ru.praktikum;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URI = "http://qa-scooter.praktikum-services.ru/";
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    public CourierClient() {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(CourierCreds creds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int courierId) {
        String DELETE_PATH = "/api/v1/courier/" + courierId;
        String body = "{\"id\":\"" + courierId + "\"}";
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .delete(DELETE_PATH)
                .then();
    }
}
