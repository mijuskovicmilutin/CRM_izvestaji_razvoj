package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.repository;

import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto.StatusLogDto;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.model.Log_FileModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class RpaRobotRepoImpl implements RpaRobotRepo{

    private final JdbcTemplate jdbcTemplate;

    public RpaRobotRepoImpl(@Qualifier("oracleBpcJdbcTemplate")JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Log_FileModel> getlogFiles(Date sysdate) {
        String sql = "SELECT   * " +
                     "FROM     bpc.log_file l " +
                     "WHERE    l.datum_vreme_obrade BETWEEN ? AND (? + 1) " +
                     "ORDER BY l.datum_vreme_obrade";

        return jdbcTemplate.query(
                sql,
                new ResultSetExtractor<List<Log_FileModel>>() {
                    @Override
                    public List<Log_FileModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        List<Log_FileModel> list = new ArrayList<>();
                        while(rs.next()){
                            Log_FileModel model = new Log_FileModel();
                            model.setSeq_id(rs.getInt("SEQ_ID"));
                            model.setBpc(rs.getString("BPC"));
                            model.setDatum_vreme_obrade(rs.getTimestamp("DATUM_VREME_OBRADE").toLocalDateTime());
                            model.setStatus_obrade(rs.getString("STATUS_OBRADE"));
                            model.setGreska(rs.getString("GRESKA"));
                            model.setProcess_instance_name(rs.getString("PROCESS_INSTANCE_NAME"));
                            model.setProcess_template_name(rs.getString("PROCESS_TEMPLATE_NAME"));
                            model.setState(rs.getString("STATE"));
                            model.setStarted(rs.getString("STARTED"));
                            model.setTracking_id(rs.getString("TRACKING_ID"));
                            model.setCorrelation_id(rs.getString("CORRELATION_ID"));
                            model.setCapi_order_number(rs.getString("CAPI_ORDER_NUMBER"));
                            model.setAction(rs.getString("ACTION"));
                            model.setMsisdn(rs.getString("MSISDN"));
                            model.setSiebel_product_id(rs.getString("SIEBEL_PRODUCT_ID"));
                            model.setAsset_integration_id(rs.getString("ASSET_INTEGRATION_ID"));
                            model.setStatus_cd(rs.getString("STATUS_CD"));
                            model.setSubstatus(rs.getString("SUBSTATUS"));
                            model.setCount_order(rs.getInt("COUNT_ORDER"));
                            model.setActivity_name(rs.getString("ACTIVITY_NAME"));
                            model.setButton(rs.getString("BUTTON"));
                            model.setActivity_status(rs.getString("ACTIVITY_STATUS"));
                            list.add(model);
                        }
                        return list;
                    }},
                sysdate,
                sysdate);
    }

    @Override
    public List<StatusLogDto> getStatusLog(Date sysdate) {
        String sql = "SELECT   f.button, f.status_obrade, count(f.seq_id) as Count " +
                     "FROM     bpc.log_file f " +
                     "WHERE    f.bpc='BAW' " +
                     "AND      f.datum_vreme_obrade BETWEEN ? AND (? + 1) " +
                     "GROUP BY f.button, f.status_obrade";

        return jdbcTemplate.query(
                sql,
                rs -> {
                    List<StatusLogDto> list = new ArrayList<>();
                    while (rs.next()){
                        list.add(
                                StatusLogDto.builder()
                                        .button(rs.getString("BUTTON"))
                                        .status_obrade(rs.getString("STATUS_OBRADE"))
                                        .count(rs.getLong("COUNT"))
                                        .build()
                        );
                    }
                    return list;
                },
                sysdate,
                sysdate);
    }
}
