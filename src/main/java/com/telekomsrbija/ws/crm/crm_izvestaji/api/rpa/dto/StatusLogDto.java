package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Builder
@Setter
@Getter
public class StatusLogDto {

    @Size(max = 50)
    private String button;

    @Size(max = 30)
    private String status_obrade;

    private Long count;
}
