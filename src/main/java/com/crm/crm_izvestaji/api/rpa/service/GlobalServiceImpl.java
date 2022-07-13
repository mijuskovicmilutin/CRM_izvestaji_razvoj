package com.crm.crm_izvestaji.api.rpa.service;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Globalni Servis sa metodama getFilename, getFileLocation, getFolderSize
*  i formatDatetoDDMMYYY (formatiranje datuma iz YYYYMMDD u DDMMYYYY), uz to definisan i globalna staticka DIRECTORY_PATH */
@Service
@Transactional
public class GlobalServiceImpl implements GlobalService{

    public static final String DIRECTORY_PATH = "D:\\Projekti\\CRM_Izvestaji\\Dokumenti\\Rpa Robot\\";

    public static String getFilename(String sysdate) {
        String fileName = ("bpc.log_file_" + sysdate + ".xlsx").replace("-", "");
        return fileName;
    }

    public static String getFileLocation(String filename) {
        // Definisanje putanje i naziva Excel dokumenta, primer - bpc.log_file20220315.xlsx
        String fileLocation = DIRECTORY_PATH.replace("\\", "/") + filename;
        return fileLocation;
    }

    public static long getFolderSize(File folder) {
        long length = 0;
        File[] files = folder.listFiles();

        int count = files.length;

        for (int i = 0; i < count; i++) {
            if (files[i].isFile()) {
                length += files[i].length();
            }
            else {
                length += getFolderSize(files[i]);
            }
        }

        return length;
    }

    //pri konvertovanju formata datuma se konvertuje i tip podatka (Date to String)
    public static Date formatDatetoDDMMYYY (String date) throws ParseException {
        SimpleDateFormat formatterYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy");
        //String to Date format (YYYY-MM-DD)
        Date dateYYYYMMDD = formatterYYYYMMDD.parse(date);
        //Date to String format (DD-MM-YYYY)
        String format = formatterDDMMYYYY.format(dateYYYYMMDD);
        //String to Date fomrat (DD-MM-YYYY)
        Date dateDDMMYYYY = formatterDDMMYYYY.parse(format);
        return dateDDMMYYYY;
    }
}
