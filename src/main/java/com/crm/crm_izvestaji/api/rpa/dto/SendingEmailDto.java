package com.crm.crm_izvestaji.api.rpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendingEmailDto {

    private String[] toEmail;
    private String[] toCC;
    private String subject;
    private String body;
    private String[] attachments;
}
