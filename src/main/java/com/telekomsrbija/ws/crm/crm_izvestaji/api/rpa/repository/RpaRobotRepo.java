package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.repository;

import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto.StatusLogDto;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.model.Log_FileModel;

import java.util.Date;
import java.util.List;

public interface RpaRobotRepo {

    List<Log_FileModel> getlogFiles (Date sysdate);

    List<StatusLogDto> getStatusLog (Date sysdate);
}
