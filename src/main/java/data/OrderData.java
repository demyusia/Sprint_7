package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;

public class OrderData {

    static Faker user = new Faker();
    public static final String FIRSTNAME = user.name().firstName();
    public static final String LASTNAME = user.name().lastName();
    public static final String ADDRESS = "Москва, Большая Грузинская улица, дом 1, строение 1";
    public static final String METRO_STATION = "2";
    public static final String PHONE = "89998887766";
    public static final String DELIVERY_DATE = String.valueOf(LocalDate.now().plusDays(2));
    public static final int RENT_TIME = 2;
    public static final String COMMENT = "No comments";
    public static final String BLACK_COLOUR = "BLACK";
    public static final String GREY_COLOUR = "GREY";

    public static final String CREATE_ORDER_PATH = "/api/v1/orders";
    public static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel";
    public static final String ACCEPT_ORDER_PATH = "/api/v1/orders/accept/{id}";
    public static final String GET_ORDER_BY_TRACK = "/api/v1/orders/track";
    public static final String GET_ORDER_LIST = "/api/v1/orders";
}
