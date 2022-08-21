package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.repository;

import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto.StatusLogDto;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.model.Log_FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RPA_RobotRepo extends JpaRepository<Log_FileModel, Integer> {

    @Query (value = "SELECT   * " +
                    "FROM     bpc.log_file l " +
                    "WHERE    l.datum_vreme_obrade > :sysdate - 1 " +
                    "ORDER BY started", nativeQuery = true)
    List<Log_FileModel> logFile (Date sysdate);

    @Query (value = "SELECT   f.button, f.status_obrade, count(f.seq_id) as Count " +
                    "FROM     bpc.log_file f " +
                    "WHERE    f.bpc='BAW' " +
                    "AND      f.datum_vreme_obrade > :sysdate - 1 " +
                    "GROUP BY f.button, f.status_obrade", nativeQuery = true)
    List<StatusLogDto> statusLog (Date sysdate);
}
