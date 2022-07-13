package com.crm.crm_izvestaji.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDtoImpl {

    private String username;

    public String password;

    public String email;

    private String emailAppPassword;

    private String name;

    private String qualifications;

    private String position;

    private String employment;

    private String address;

    private String phoneNumber;
}
