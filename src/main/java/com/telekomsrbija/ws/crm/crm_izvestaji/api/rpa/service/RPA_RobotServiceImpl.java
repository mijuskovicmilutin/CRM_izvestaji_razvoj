package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.service;

import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.excel.GenerateExcel;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.mailsender.MailSenderService;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.repository.RpaRobotRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

import static com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.service.GlobalServiceImpl.formatDatetoDDMMYYY;

@Service
@Transactional
public class RPA_RobotServiceImpl implements RPA_RobotService{

    private final GenerateExcel generateExcel;
    private final MailSenderService mailSenderService;
    private final RpaRobotRepo rpaRobotRepo;

    public RPA_RobotServiceImpl(GenerateExcel generateExcel, MailSenderService mailSenderService, JdbcTemplate jdbcTemplate, RpaRobotRepo rpaRobotRepo) {
        this.generateExcel = generateExcel;
        this.mailSenderService = mailSenderService;
        this.rpaRobotRepo = rpaRobotRepo;
    }

    /* Servisna Metoda za generisanje Excel fajla, pozivanje GenerateExcel metode iz (servis) klase u kome se nalazi logika generisanja Excel fajla
    *  i pozivanje RPA_RobotRepo repozitorijuma u kome se nalazi Query (upit) koji se ispucava na bazi*/
    @Override
    public String rpa_robotGenerateExcel (String sysdate) {
        //sysdate format yyyy-MM-dd
        //date    format dd-MM-yyyy
        try {
            Date date = formatDatetoDDMMYYY(sysdate);
            String attachment = generateExcel.generateExcel(rpaRobotRepo.getlogFiles(date), rpaRobotRepo.getStatusLog(date), sysdate);
            return attachment;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /* Servisna Metoda za slanje Email-a, pozivanje MailSenderService metode iz (servis) klase u kome se nalazi logika slanja Email-a*/
    @Override
    public void rpa_robotSendingEmail (String[] toEmail, String[] toCC, String body, String subject, String[] attachments){
        try {
            mailSenderService.sendMailWithAttachment(toEmail, toCC, body, subject, attachments);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
