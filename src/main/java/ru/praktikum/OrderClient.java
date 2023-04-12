package ru.praktikum;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient {
        private static final String BASE_URI = "http://qa-scooter.praktikum-services.ru/";
        private static final String ORDER_PATH = "/api/v1/orders";

        public OrderClient() {
            RestAssured.baseURI = BASE_URI;
        }

        @Step("Создание заказа")
        public ValidatableResponse create(Order order) {
            return given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(order)
                    .when()
                    .post(ORDER_PATH)
                    .then();
        }

    @Step("Получение списка заказов")
    public ValidatableResponse getList() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .get(ORDER_PATH)
                .then();
    }
}
