package ru.praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParamTest {

    private final String firstName;
    private final String  lastName;
    private final String  address;
    private final String metroStation;
    private final String  phone;
    private final int  rentTime;
    private final String  deliveryDate;
    private final String  comment;
    private final List<String> color;
    private Order order;
    private OrderClient orderClient;

    public CreateOrderParamTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Создание заказа с цветом - {8}")
    public static Object[][] getOrderParam() {
        return new Object[][] {
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2023-06-06", "Нужен только черного цвета!", List.of("BLACK")},
                {"Иван", "Иванов", "Москва, Мира 20", "1", "89876543210", 4, "2023-05-06", "Нужен только серого цвета!", List.of("GREY")},
                {"Петр", "Петров", "Санкт Петербург, Мира 45", "2", "8-987-654-32-10", 2, "2023-04-12", "Можно черного или серого цвета!", List.of("BLACK", "GREY")},
                {"Сергей", "Сергеев", "Новосибирск, Ленина 12", "3", "+7(800)355-35-35", 1, "2023-04-30", "Не важно какого цвета!", List.of()},
        };
    }

    @Before
    public void setUp() {
         orderClient = new OrderClient();
         order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    @Test
    public void createOrderParamTest() {
        ValidatableResponse response = orderClient.create(order);
        response.statusCode(SC_CREATED).and().body("track", notNullValue());
    }
}
