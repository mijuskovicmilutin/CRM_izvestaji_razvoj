package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.mailsender;

import com.telekomsrbija.ws.crm.crm_izvestaji.users.service.UserServiceImpl;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.service.GlobalServiceImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

@Service
@Transactional
public class MailSenderService {

    /* JavaMailSenderImpl je klasa Spring Boot-a za konfiguraciju slanja Email-a. Pripada javax.mail biblioteci.*/
    private final JavaMailSenderImpl javaMailSender;
    private final UserServiceImpl userService;

    public MailSenderService(JavaMailSenderImpl javaMailSender, UserServiceImpl userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    /* Metoda konfiguracije slanje Email-a pomocu MimeMessageHelper.*/
    public void sendMailWithAttachment (String[] toEmail, String[] toCC, String body, String subject, String[] attachments)
            throws MessagingException {

        javaMailSender.setUsername(userService.getUserEmail());
        javaMailSender.setPassword(userService.getUserEmailAppPassword());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(userService.getUserEmail());
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setCc(toCC);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlBodyAndSignature(body, userService.getUserName(), userService.getUserQualifications(),
                userService.getUserPosition(), userService.getUserEmployment(), userService.getUserAddress(),
                userService.getUserPhoneNum()), true);

        Iterator<String> attachment = Arrays.stream(attachments).iterator();
        while (attachment.hasNext()){
            String fileLocation = GlobalServiceImpl.getFileLocation(attachment.next());
            FileSystemResource fileSystemResource = new FileSystemResource(new File(fileLocation));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
        }
        javaMailSender.send(mimeMessage);
        System.out.println("Uspesno poslat mail.");
    }

    /* Metoda za generisanje html body za slanje Email-a. HTML sadrzi nekoliko promenljivih ${body},${name},${qualifications},
    *  ${position},${employment},${address},${phoneNumber} od kojih su svi sem ${body} parametri za potpis u mejlu (signature)
    *  i povlace se iz UserRepo. Promenljiva ${body} se prosledjuje sa fronta i ulazni je parametar sendMailWithAttachment metode*/
    public String htmlBodyAndSignature (String body, String name, String qualifications, String position, String employment,
                            String address, String phoneNumber){

        String htmlBody = "<body\n" +
                "  lang=\"SR-LATN-RS\"\n" +
                "  link=\"#0563C1\"\n" +
                "  vlink=\"#954F72\"\n" +
                "  style=\"word-wrap:break-word\"\n" +
                ">\n" +
                "  <div class=\"WordSection1\">\n" +
                "    <p class=\"MsoNormal\">${body}</p>\n" +
                "    <div>\n" +
                "      <table\n" +
                "        class=\"MsoNormalTable\"\n" +
                "        border=\"0\"\n" +
                "        cellspacing=\"0\"\n" +
                "        cellpadding=\"0\"\n" +
                "        style=\"border-collapse:collapse\"\n" +
                "      >\n" +
                "        <tbody>\n" +
                "          <tr style=\"height:14.4pt\">\n" +
                "            <td\n" +
                "              width=\"492\"\n" +
                "              style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:14.4pt\"\n" +
                "            >\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "                <b>\n" +
                "                  <span\n" +
                "                    style='font-size:10.5pt;line-height:105%;font-family:\"Verdana\",sans-serif;color:#ED1A3B;mso-fareast-language:SR-LATN-RS'\n" +
                "                    >${name}, ${qualifications}</span\n" +
                "                  >\n" +
                "                </b>\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\"> </span>\n" +
                "              </p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr style=\"height:14.4pt\">\n" +
                "            <td\n" +
                "              width=\"492\"\n" +
                "              style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:14.4pt\"\n" +
                "            >\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\">\n" +
                "                ${position}\n" +
                "                </span>\n" +
                "              </p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr style=\"height:2.25pt\">\n" +
                "            <td\n" +
                "              width=\"492\"\n" +
                "              style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:2.25pt\"\n" +
                "            >\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "                <span\n" +
                "                  style='font-size:3.0pt;line-height:105%;font-family:\"Verdana\",sans-serif;color:#ED1A3B;mso-fareast-language:SR-LATN-RS'\n" +
                "                  >─────────────</span\n" +
                "                >\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\"> </span>\n" +
                "              </p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr style=\"height:15.0pt\">\n" +
                "            <td\n" +
                "              width=\"492\"\n" +
                "              style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:15.0pt\"\n" +
                "            >\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "                <span\n" +
                "                  style='font-size:9.0pt;line-height:105%;font-family:\"Verdana\",sans-serif;color:#646565;mso-fareast-language:SR-LATN-RS'\n" +
                "                  >${employment}</span\n" +
                "                >\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\"> </span>\n" +
                "              </p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr style=\"height:2.25pt\">\n" +
                "            <td\n" +
                "              width=\"492\"\n" +
                "              style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:2.25pt\"\n" +
                "            >\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "                <span\n" +
                "                  style='font-size:3.0pt;line-height:105%;font-family:\"Verdana\",sans-serif;color:#ED1A3B;mso-fareast-language:SR-LATN-RS'\n" +
                "                  >─────────────</span\n" +
                "                >\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\"> </span>\n" +
                "              </p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr style=\"height:5.05pt\">\n" +
                "            <td\n" +
                "              width=\"492\"\n" +
                "              style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:5.05pt\"\n" +
                "            >\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "                <span\n" +
                "                  style='font-size:9.0pt;line-height:105%;font-family:\"Verdana\",sans-serif;color:#646565;mso-fareast-language:SR-LATN-RS'\n" +
                "                  >${address}\n" +
                "                </span>\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\"> </span>\n" +
                "              </p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr style=\"height:3.0pt\">\n" +
                "            <td\n" +
                "              width=\"492\"\n" +
                "              style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:3.0pt\"\n" +
                "            >\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "                <span\n" +
                "                  style='font-size:9.0pt;line-height:105%;font-family:\"Verdana\",sans-serif;color:#ED1A3B;mso-fareast-language:SR-LATN-RS'\n" +
                "                  >•</span\n" +
                "                >\n" +
                "                <span\n" +
                "                  style='font-size:9.0pt;line-height:105%;font-family:\"Verdana\",sans-serif;color:#646565;mso-fareast-language:SR-LATN-RS'\n" +
                "                  >${phoneNumber}</span\n" +
                "                >\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\"> </span>\n" +
                "              </p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr style=\"height:38.35pt\">\n" +
                "          <td width=\"492\" valign=\"bottom\" style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:38.35pt\">\n" +
                "            <p class=\"MsoNormal\" style=\"line-height:105%\">\n" +
                "              <span style=\"font-family:&quot;Verdana&quot;,sans-serif;mso-fareast-language:SR-LATN-RS\">\n" +
                "                <img width=\"220\" height=\"54\" style=\"width:2.2916in;height:.5625in\" id=\"Picture_x0020_4\" src=\"data:image/jpeg;base64,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\" alt=\"Telekom Mts \"/>\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\">\n" +
                "                </span>\n" +
                "              </span>\n" +
                "              </p>\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%\">\n" +
                "                <a href=\"https://mts.rs/Privatni/Korisnicka-zona/Vesti/Promocije/a88711-Mts-je-najbolja-mreza-u-Srbiji-RATEL-mts-Tvoj-svet.html\">\n" +
                "                  <span style=\"font-family:&quot;Verdana&quot;,sans-serif;color:windowtext;mso-fareast-language:SR-LATN-RS;text-decoration:none\">\n" +
                "                    <img border=\"0\" width=\"220\" height=\"77\" style=\"width:2.2916in;height:.802in\" id=\"Picture_x0020_5\" src=\"data:image/jpeg;base64,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\"/>\n" +
                "                  </span>\n" +
                "                  </a>\n" +
                "                  <span style=\"mso-fareast-language:SR-LATN-RS\">\n" +
                "                  </span>\n" +
                "                </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr style=\"height:7.3pt\">\n" +
                "            <td width=\"492\" style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:7.3pt\">\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "                <span style=\"font-size:6.0pt;line-height:105%;font-family:&quot;Verdana&quot;,sans-serif;color:#646565;mso-fareast-language:SR-LATN-RS\">Skrećemo vam pažnju da se na svu elektronsku korespondenciju Telekom\n" +
                "Srbija a.d., </span>\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\">\n" +
                "                </span>\n" +
                "              </p>\n" +
                "              <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "                <span style=\"font-size:6.0pt;line-height:105%;font-family:&quot;Verdana&quot;,sans-serif;color:#646565;mso-fareast-language:SR-LATN-RS\">kako internu tako i eksternu, primenjuju Pravila koja su dostupna\n" +
                "na </span>\n" +
                "                <span style=\"font-size:6.0pt;line-height:105%;font-family:&quot;Verdana&quot;,sans-serif;color:#ED1A3B;mso-fareast-language:SR-LATN-RS\">\n" +
                "                  <a href=\"http://www.mts.rs/otelekomu/disclaimer\">\n" +
                "                    <span style=\"font-family:&quot;Calibri&quot;,sans-serif;color:#ED1A3B;text-decoration:none\">disclaimer</span>\n" +
                "                  </a>\n" +
                "                </span>\n" +
                "                <span style=\"mso-fareast-language:SR-LATN-RS\">\n" +
                "                </span>\n" +
                "              </p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr style=\"height:2.25pt\">\n" +
                "          <td width=\"492\" style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:2.25pt\">\n" +
                "            <p class=\"MsoNormal\" style=\"line-height:105%;text-autospace:none\">\n" +
                "              <span style=\"font-size:3.0pt;line-height:105%;font-family:&quot;Verdana&quot;,sans-serif;color:white;mso-fareast-language:SR-LATN-RS\">─</span>\n" +
                "              <span style=\"mso-fareast-language:SR-LATN-RS\">\n" +
                "              </span>\n" +
                "            </p>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <tr style=\"height:8.5pt\">\n" +
                "          <td width=\"492\" style=\"width:368.65pt;padding:0cm .5pt 0cm .5pt;height:8.5pt\">\n" +
                "            <table class=\"MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100.0%\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td width=\"30\" style=\"width:22.5pt;padding:0cm 0cm 0cm 0cm\">\n" +
                "                    <p class=\"MsoNormal\" style=\"line-height:105%\">\n" +
                "                      <span style=\"font-family:&quot;Verdana&quot;,sans-serif;color:#1F497D;mso-fareast-language:SR-LATN-RS\">\n" +
                "                        <img border=\"0\" width=\"20\" height=\"21\" style=\"width:.2083in;height:.2187in\" id=\"Picture_x0020_6\" src=\"cid:image006.png@01D88C86.694F7170\"/>\n" +
                "                        <span style=\"mso-fareast-language:SR-LATN-RS\">\n" +
                "                        </span>\n" +
                "                      </span>\n" +
                "                      </p>\n" +
                "                    </td>\n" +
                "                    <td width=\"574\" style=\"width:430.55pt;padding:0cm 0cm 0cm 0cm\">\n" +
                "                      <p class=\"MsoNormal\" style=\"line-height:105%\">\n" +
                "                        <span style=\"font-size:6.0pt;line-height:105%;font-family:&quot;Verdana&quot;,sans-serif;color:#646565;mso-fareast-language:SR-LATN-RS\">Sačuvajmo drveće. Ako nije neophodno, nemojte štampati ovu poruku.<br>\n" +
                "Save a tree. Don’t print this message unless it’s necessary.</span>\n" +
                "                          <span style=\"mso-fareast-language:SR-LATN-RS\">\n" +
                "                          </span>\n" +
                "                        </p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </tbody>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n";

        String replacedBody = body.replace("/n", "<br>");
        String replacedHtmlBody = htmlBody.replace("${body}", replacedBody)
                                          .replace("${name}", name)
                                          .replace("${qualifications}", qualifications)
                                          .replace("${position}", position)
                                          .replace("${employment}", employment)
                                          .replace("${address}", address)
                                          .replace("${phoneNumber}", phoneNumber);
        return replacedHtmlBody;
    }
}
