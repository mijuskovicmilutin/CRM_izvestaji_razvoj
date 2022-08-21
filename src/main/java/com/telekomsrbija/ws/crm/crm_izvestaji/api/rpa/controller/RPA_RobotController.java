package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.controller;

import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto.CustomResponseEntity;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto.DownloadExcelDto;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto.GenerateExcelDto;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto.SendingEmailDto;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.service.RPA_RobotService;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.service.GlobalServiceImpl;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.Paths.get;

@RestController
@Validated
@RequestMapping("/api/rpa")
public class RPA_RobotController {

    private final RPA_RobotService rpaRobotService;

    public RPA_RobotController(RPA_RobotService rpaRobotService) {
        this.rpaRobotService = rpaRobotService;
    }


    /* Post metoda za generisanje Excel fajla
    *  Ulazni parametar: datum; Izlazni parametar: naziv fajla (pr. bpc.log_file20220713)*/
    @PostMapping ("/excel/generate")
    public ResponseEntity<?> generateExcel (@Valid @RequestBody GenerateExcelDto generateExcelDto) {
        String filename = rpaRobotService.rpa_robotGenerateExcel(generateExcelDto.getSysdate());
        return new ResponseEntity<>(CustomResponseEntity.generateResponse(HttpStatus.OK, filename), HttpStatus.OK);
    }

    /* Post metoda za download generisanog Excel fajla
    *  Ulazni parametar: naziv fajla; Izlazni parametar: Resource fajl pripremljen za download
       - Directory_path je potrebno uraditi replace \\ sa / jer Java ne moze da radi sa ovim karakterom \\
       - Da bi fajl bio pripremljen za download potrebno je u Response postaviti Header - Content_Disposition koji nosi
       potrebne informacije o body Response-a u kom se nalazi Resource fajla*/
    @PostMapping ("/excel/download")
    public ResponseEntity<Resource> downloadExcel (@Valid @RequestBody DownloadExcelDto downloadExcelDto) throws IOException {
        String filename = downloadExcelDto.getFilename().replace("-", "");
        String fileLocation = GlobalServiceImpl.DIRECTORY_PATH.replace("\\", "/");
        Path filePath = get(fileLocation).toAbsolutePath().normalize().resolve(filename);
        if (!Files.exists(filePath)){
            throw new FileNotFoundException(downloadExcelDto.getFilename() + " was not found on the server.");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment:File-Name=" + filename);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }

    /* Post metoda za slanje Email-a
    *  Ulazni parametri: toEmail[], toCC[], subject, body, attachment (naziv fajla); Izlazni parameta: status code*/
    @PostMapping ("/email")
    public ResponseEntity<?> sendEmail (@Valid @RequestBody SendingEmailDto sendingEmailDto) throws MessagingException {
        rpaRobotService.rpa_robotSendingEmail(sendingEmailDto.getToEmail(),
                                              sendingEmailDto.getToCC(),
                                              sendingEmailDto.getBody(),
                                              sendingEmailDto.getSubject(),
                                              sendingEmailDto.getAttachments());
        return new ResponseEntity<>(CustomResponseEntity.generateResponse(HttpStatus.OK, "Uspesno poslat email."), HttpStatus.OK);
    }
}
