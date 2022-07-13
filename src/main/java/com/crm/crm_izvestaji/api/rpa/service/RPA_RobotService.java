package com.crm.crm_izvestaji.api.rpa.service;

import javax.mail.MessagingException;

public interface RPA_RobotService {

    String rpa_robotGenerateExcel (String sysdate);
    void rpa_robotSendingEmail (String[] toEmail, String[] toCC, String body, String subject, String[] attachments) throws MessagingException;
}
