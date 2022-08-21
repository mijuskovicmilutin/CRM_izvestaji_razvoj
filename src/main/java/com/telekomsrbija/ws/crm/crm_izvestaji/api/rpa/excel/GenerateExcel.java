package com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.excel;

import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.dto.StatusLogDto;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.model.Log_FileModel;
import com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.repository.RPA_RobotRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.telekomsrbija.ws.crm.crm_izvestaji.api.rpa.service.GlobalServiceImpl.*;

// Klasa za generisanje Excel fajla, podacima koji se dobijaju iz RPA_RobotRepo repozitorijuma
@Service
@Transactional
public class GenerateExcel {

    private final RPA_RobotRepo rpa_robotRepo;
    public static final Long MAXIMUM_FOLDER_SIZE = Long.valueOf(209715200); // 209715200 bytes = 200MB

    public GenerateExcel(RPA_RobotRepo rpa_robotRepo) {
        this.rpa_robotRepo = rpa_robotRepo;
    }

    public String generateExcel (List<Log_FileModel> logFileDtoList, List<StatusLogDto> statusLogDtoList, String sysdate) {

        String filename = getFilename(sysdate);
        String fileLocation = getFileLocation(filename);
        File file = new File(fileLocation);

        Long folderSize = getFolderSize(new File(DIRECTORY_PATH));

        if (folderSize < MAXIMUM_FOLDER_SIZE) {

            try {
                if (!file.exists()) {

                    // Pokretanje biblioteke za generisanje Excel fajla
                    Workbook workbook = new XSSFWorkbook();

                    // Kreiranje SHEET Log_File
                    Sheet logFile = workbook.createSheet("Log_file");

                    Row header = logFile.createRow(0);

                    // Header style i font
                    CellStyle headerStyle = workbook.createCellStyle();
                    headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                    XSSFFont font = (XSSFFont) workbook.createFont();
                    font.setFontName("Arial");
                    font.setFontHeightInPoints((short) 10);
                    font.setBold(true);
                    headerStyle.setFont(font);

                    // Upis naziva kolona u Header ciji se spisak nalazi u Enum HeaderValuesLogFile
                    for (int i = 0; i < HeaderValuesLogFile.values().length; i++) {
                        Cell cell = header.createCell(i);
                        cell.setCellStyle(headerStyle);
                        cell.setCellValue(String.valueOf(HeaderValuesLogFile.values()[i]));
                    }

                    CellStyle style = workbook.createCellStyle();
                    style.setWrapText(true);

                    // Upis rekorda u Excel
                    for (int i = 0; i < logFileDtoList.size(); i++) {
                        Row row = logFile.createRow(i + 1);
                        Cell cell = row.createCell(0);
                        cell.setCellValue(logFileDtoList.get(i).getSeq_id());
                        cell.setCellStyle(style);

                        cell = row.createCell(1);
                        cell.setCellValue(logFileDtoList.get(i).getBpc());
                        cell = row.createCell(2);
                        String datum_vreme_obrade = (logFileDtoList.get(i).getDatum_vreme_obrade().toString());
                        cell.setCellValue(datum_vreme_obrade.replace("T", " "));
                        cell = row.createCell(3);
                        cell.setCellValue(logFileDtoList.get(i).getStatus_obrade());
                        cell = row.createCell(4);
                        cell.setCellValue(logFileDtoList.get(i).getGreska());
                        cell = row.createCell(5);
                        cell.setCellValue(logFileDtoList.get(i).getProcess_instance_name());
                        cell = row.createCell(6);
                        cell.setCellValue(logFileDtoList.get(i).getProcess_template_name());
                        cell = row.createCell(7);
                        cell.setCellValue(logFileDtoList.get(i).getState());
                        cell = row.createCell(8);
                        cell.setCellValue(logFileDtoList.get(i).getStarted());
                        cell = row.createCell(9);
                        cell.setCellValue(logFileDtoList.get(i).getTracking_id());
                        cell = row.createCell(10);
                        cell.setCellValue(logFileDtoList.get(i).getCorrelation_id());
                        cell = row.createCell(11);
                        cell.setCellValue(logFileDtoList.get(i).getCapi_order_number());
                        cell = row.createCell(12);
                        cell.setCellValue(logFileDtoList.get(i).getAction());
                        cell = row.createCell(13);
                        cell.setCellValue(logFileDtoList.get(i).getMsisdn());
                        cell = row.createCell(14);
                        cell.setCellValue(logFileDtoList.get(i).getSiebel_product_id());
                        cell = row.createCell(15);
                        cell.setCellValue(logFileDtoList.get(i).getAsset_integration_id());
                        cell = row.createCell(16);
                        cell.setCellValue(logFileDtoList.get(i).getStatus_cd());
                        cell = row.createCell(17);
                        cell.setCellValue(logFileDtoList.get(i).getSubstatus());
                        cell = row.createCell(18);
                        if (logFileDtoList.get(i).getCount_order() != null) {
                            cell.setCellValue(logFileDtoList.get(i).getCount_order().intValue());
                        } else {
                            cell.setCellValue("");
                        }
                        cell = row.createCell(19);
                        cell.setCellValue(logFileDtoList.get(i).getActivity_name());
                        cell = row.createCell(20);
                        cell.setCellValue(logFileDtoList.get(i).getButton());
                        cell = row.createCell(21);
                        cell.setCellValue(logFileDtoList.get(i).getActivity_status());
                    }
                    ;
                    // Autosize kolona u odnosu na duzinu Header i rekorda
                    for (int i = 0; i < HeaderValuesLogFile.values().length; i++) {
                        logFile.autoSizeColumn(i);
                    }


                    //  Kreiranje SHEET Status_Log
                    Sheet statusLog = workbook.createSheet("Status_Log");

                    Row headerS = statusLog.createRow(0);

                    // Upis naziva kolona u Header ciji se spisak nalazi u Enum HeaderValuesStatusLog
                    for (int i = 0; i < HeaderValuesStatusLog.values().length; i++) {
                        Cell cell = headerS.createCell(i);
                        cell.setCellStyle(headerStyle);
                        cell.setCellValue(String.valueOf(HeaderValuesStatusLog.values()[i]));
                    }

                    // Upis rekorda u Excel
                    for (int i = 0; i < statusLogDtoList.size(); i++) {
                        Row row = statusLog.createRow(i + 1);
                        Cell cell = row.createCell(0);
                        if (statusLogDtoList.get(i).getButton() != null) {
                            cell.setCellValue(statusLogDtoList.get(i).getButton());
                        } else {
                            cell.setCellValue("");
                        }
                        cell.setCellStyle(style);

                        cell = row.createCell(1);
                        cell.setCellValue(statusLogDtoList.get(i).getStatus_obrade());
                        cell = row.createCell(2);
                        if (statusLogDtoList.get(i).getCount() != null) {
                            cell.setCellValue(statusLogDtoList.get(i).getCount());
                        } else {
                            cell.setCellValue("");
                        }
                    }
                    // Autosize kolona u odnosu na duzinu Header i rekorda
                    for (int i = 0; i < HeaderValuesStatusLog.values().length; i++) {
                        statusLog.autoSizeColumn(i);
                    }

                    // Generisanje dokumenta
                    FileOutputStream outputStream = new FileOutputStream(fileLocation);
                    workbook.write(outputStream);
                    // Zatvaranje biblioteke
                    workbook.close();


                    System.out.println("Excel File je generisan.");
                }
                return filename;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            throw new OutOfMemoryError();
        }
    }
}
