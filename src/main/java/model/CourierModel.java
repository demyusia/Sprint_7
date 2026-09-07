package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourierModel {

    private String login;
    private String password;
    private String firstName;


    public CourierModel(String password, String login) {
        this.password = password;
        this.login = login;
        this.firstName = null;
    }

}
