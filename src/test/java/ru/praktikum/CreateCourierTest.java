package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.CourierCreds.credsFrom;

public class CreateCourierTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера со всеми полями")
    public void createCourierWithAllFieldsTest() {
        courier = new Courier().courierGenerator();
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(SC_CREATED).and().assertThat().body("ok",  equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера только с обязательными полями (без поля first name)")
    public void createCourierWithoutFirstNameTest() {
        courier = new Courier().withoutFirstName();
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(SC_CREATED).and().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Нельзя создать курьера без поля логин")
    public void createCourierWithoutLoginTest() {
        courier = new Courier().withoutLogin();
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(SC_BAD_REQUEST).and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без поля пароль")
    public void createCourierWithoutPasswordTest() {
        courier = new Courier().withoutPassword();
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(SC_BAD_REQUEST).and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void createCourierWithSameLoginTest() {
        courier = new Courier().courierGenerator();
        courierClient.create(courier);
        ValidatableResponse response2 = courierClient.create(courier);
        response2.statusCode(SC_CONFLICT).and().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void tearDown() {
        ValidatableResponse loginResponse = courierClient.login(credsFrom(courier));
        if (loginResponse.extract().path("id") != null) {
            int courierId = loginResponse.extract().path("id");
            courierClient.delete(courierId);
        }
    }

}
