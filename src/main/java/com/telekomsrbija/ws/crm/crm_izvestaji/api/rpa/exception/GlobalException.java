package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.exception;

import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto.CustomResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOFileException(IOException exception){
        exception.printStackTrace();
        return new ResponseEntity<>(CustomResponseEntity.generateResponse(HttpStatus.FORBIDDEN, "Greška pri učitavanju fajla."), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(FileNotFoundException exception){
        exception.printStackTrace();
        return new ResponseEntity<>(CustomResponseEntity.generateResponse( HttpStatus.NOT_FOUND, "Fajl nije pronađen."),  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<?> handleJobException(InterruptedException exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(CustomResponseEntity.generateResponse(HttpStatus.FORBIDDEN, "Greška, došlo je do prekida džoba."), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleMessageEmailException(MessagingException exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(CustomResponseEntity.generateResponse(HttpStatus.FORBIDDEN, "Greška pri slanju mejla."), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<?> handleSendEmailException(MailSendException exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(CustomResponseEntity.generateResponse(HttpStatus.FORBIDDEN, "Greška pri slanju mejla."), HttpStatus.FORBIDDEN);
    }
}
