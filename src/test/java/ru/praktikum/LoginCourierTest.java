package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {

    private Courier courier;
    private CourierCreds creds;
    private CourierClient courierClient;
    private ValidatableResponse response;

    @Before
        public void setUp() {
        courier = new Courier().courierGenerator();
        courierClient = new CourierClient();
        courierClient.create(courier);
    }

    @Test
    @DisplayName("Авторизация курьера с логином и паролем")
    public void loginCourierWithRequiredFieldsTest() {
        creds = CourierCreds.credsFrom(courier);
        response = courierClient.login(creds);
        response.statusCode(SC_OK).and().assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Нельзя авторизоваться без логина")
    public void loginCourierWithoutLoginTest() {
        creds = CourierCreds.credsWithoutLoginFrom(courier);
        response = courierClient.login(creds);
        response.statusCode(SC_BAD_REQUEST).and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться без пароля")
    public void loginCourierWithoutPasswordTest() {
        creds = CourierCreds.credsWithoutPasswordFrom(courier);
        response = courierClient.login(creds);
        response.statusCode(SC_BAD_REQUEST).and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться под не существующим пользователем")
    public void loginCourierWithUnRegisteredUserTest() {
        Courier courierUnRegistered = new Courier().courierGenerator();
        CourierCreds credsUnRegistered = CourierCreds.credsFrom(courierUnRegistered);
        response = courierClient.login(credsUnRegistered);
        response.statusCode(SC_NOT_FOUND).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        if (response.extract().path("id") != null) {
            int courierId = response.extract().path("id");
            courierClient.delete(courierId);
        }
    }
}
