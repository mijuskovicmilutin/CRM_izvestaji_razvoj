package com.crm.crm_izvestaji.users.dto;

import java.io.Serializable;

public interface UserDto extends Serializable {

    String getUsername();

    String getPassword();

    String getEmail();

    String getEmailAppPassword();

    String getName();

    String getQualifications();

    String getPosition();

    String getEmployment();

    String address();

    String phoneNumber();
}
