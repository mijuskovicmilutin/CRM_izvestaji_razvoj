package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.schedule;

import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.service.GlobalServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduleJobConfig {


    /* Job za brisanje fajlova u direktorijumu DIRECTORY_PATH koji su stariji od 7 dana. */
    // cron = "sec min hours days months dayperweek" - cron = "0 0 15 * * *" se odnosi na job svakog dana u 15:00h
    @Async
    @Scheduled(cron = "00 57 09 * * *")
    public void deleteOldFiles() throws InterruptedException, IOException {

        File targetDir = new File(GlobalServiceImpl.DIRECTORY_PATH);
        if (targetDir.exists()){
            for (File file : targetDir.listFiles()){
                Long oldFiles = new Date().getTime() - file.lastModified();
                if (oldFiles > 7 * 24 * 60 * 60 * 1000) {
                    file.delete();
                    System.out.println("Obrisan je dokument:" + file.getName());
                }
            }
        }
    }
}
