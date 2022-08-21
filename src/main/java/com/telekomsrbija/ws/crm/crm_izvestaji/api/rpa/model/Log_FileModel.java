package com.crm.crm_izvestaji.api.rpa.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table (schema = "bpc", name = "log_file")
@Getter
public class Log_FileModel {

    @Id
    private int seq_id;
    @Size(max = 3)
    private String bpc;
    private LocalDateTime datum_vreme_obrade;
    @Size(max = 30)
    private String status_obrade;
    @Size(max = 2000)
    private String greska;
    @Size(max = 200)
    private String process_instance_name;
    @Size(max = 200)
    private String process_template_name;
    @Size(max = 30)
    private String state;
    @Size(max = 100)
    private String started;
    @Size(max = 200)
    private String tracking_id;
    @Size(max = 30)
    private String correlation_id;
    @Size(max = 200)
    private String capi_order_number;
    @Size(max = 30)
    private String action;
    @Size(max = 30)
    private String msisdn;
    @Size(max = 50)
    private String siebel_product_id;
    @Size(max = 100)
    private String asset_integration_id;
    @Size(max = 20)
    private String status_cd;
    @Size(max = 100)
    private String substatus;
    private Integer count_order;
    @Size(max = 100)
    private String activity_name;
    @Size(max = 50)
    private String button;
    @Size(max = 50)
    private String activity_status;
}
