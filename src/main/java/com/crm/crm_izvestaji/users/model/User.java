package com.crm.crm_izvestaji.users.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String username;
    private String password;
    private String email;
    private String emailAppPassword;
    private String name;
    private String qualifications;
    private String position;
    private String employment;
    private String address;
    private String phoneNumber;
}
