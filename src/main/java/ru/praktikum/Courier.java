package ru.praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {}

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public Courier courierGenerator() {
        return new  Courier(RandomStringUtils.randomAlphanumeric(5, 8),
                RandomStringUtils.randomAlphanumeric(5, 8),
                RandomStringUtils.randomAlphanumeric(5, 8));
    }

    public Courier withoutLogin() {
        return new  Courier("",
                RandomStringUtils.randomAlphanumeric(5, 8),
                RandomStringUtils.randomAlphanumeric(5, 8));
    }

    public Courier withoutPassword() {
        return new  Courier(RandomStringUtils.randomAlphanumeric(5, 8),
                "",
                RandomStringUtils.randomAlphanumeric(5, 8));
    }

    public Courier withoutFirstName() {
        return new  Courier(RandomStringUtils.randomAlphanumeric(5, 8),
                RandomStringUtils.randomAlphanumeric(5, 8),
                "");
    }
}
