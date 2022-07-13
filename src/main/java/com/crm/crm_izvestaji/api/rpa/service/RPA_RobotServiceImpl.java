package com.crm.crm_izvestaji.api.rpa.service;

import com.crm.crm_izvestaji.api.rpa.excell.GenerateExcel;
import com.crm.crm_izvestaji.api.rpa.mailsender.MailSenderService;
import com.crm.crm_izvestaji.api.rpa.repository.RPA_RobotRepo;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;

import static com.crm.crm_izvestaji.api.rpa.service.GlobalServiceImpl.formatDatetoDDMMYYY;

@Service
@Transactional
public class RPA_RobotServiceImpl implements RPA_RobotService{

    private final RPA_RobotRepo rpa_robotRepo;
    private final GenerateExcel generateExcel;
    private final MailSenderService mailSenderService;

    public RPA_RobotServiceImpl(RPA_RobotRepo rpa_robotRepo, GenerateExcel generateExcel, MailSenderService mailSenderService) {
        this.rpa_robotRepo = rpa_robotRepo;
        this.generateExcel = generateExcel;
        this.mailSenderService = mailSenderService;
    }

    /* Servisna Metoda za generisanje Excel fajla, pozivanje GenerateExcel metode iz (servis) klase u kome se nalazi logika generisanja Excel fajla
    *  i pozivanje RPA_RobotRepo repozitorijuma u kome se nalazi Query (upit) koji se ispucava na bazi*/
    @Override
    public String rpa_robotGenerateExcel (String sysdate) {
        //sysdate format yyyy-MM-dd
        //date    format dd-MM-yyyy
        try {
            Date date = formatDatetoDDMMYYY(sysdate);
            String attachment = generateExcel.generateExcel(rpa_robotRepo.logFile(date), rpa_robotRepo.statusLog(date), sysdate);
            return attachment;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /* Servisna Metoda za slanje Email-a, pozivanje MailSenderService metode iz (servis) klase u kome se nalazi logika slanja Email-a*/
    @Override
    public void rpa_robotSendingEmail (String[] toEmail, String[] toCC, String body, String subject, String[] attachments) throws MessagingException{
        try {
            mailSenderService.sendMailWithAttachment(toEmail, toCC, body, subject, attachments);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
