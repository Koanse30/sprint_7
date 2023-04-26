package ru.praktikum;

public class CourierCreds {

    private String login;
    private String password;

    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds credsFrom(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }

    public static CourierCreds credsWithoutLoginFrom(Courier courier) {
        return new CourierCreds("", courier.getPassword());
    }

    public static CourierCreds credsWithoutPasswordFrom(Courier courier) {
        return new CourierCreds(courier.getLogin(), "");
    }
}
