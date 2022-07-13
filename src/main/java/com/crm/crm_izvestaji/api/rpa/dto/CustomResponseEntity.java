package com.crm.crm_izvestaji.api.rpa.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponseEntity {

    public static ResponseEntity<Object> generateResponse (HttpStatus statusCode, Object responseObject){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            map.put("statusCode", statusCode.value());
            map.put("data", responseObject);

            return new ResponseEntity<Object>(map, statusCode);
        }catch (Exception e){
            map.clear();
            map.put("statusCode", statusCode.value());
            map.put("data", null);

            return new ResponseEntity<Object>(map, statusCode);
        }
    }
}
