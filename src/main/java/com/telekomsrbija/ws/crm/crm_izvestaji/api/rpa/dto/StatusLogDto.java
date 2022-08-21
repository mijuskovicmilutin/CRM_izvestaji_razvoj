package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto;


import javax.validation.constraints.Size;

public interface StatusLogDto {

    @Size(max = 50)
    String getButton();
    @Size(max = 30)
    String getStatus_obrade();

    Long getCount();
}
