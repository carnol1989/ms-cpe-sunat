package com.epsgrau.pe.helper;

import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    //xls=application/vnd.ms-excel
    //xlsx=application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
    public static boolean hasExcelFormat(MultipartFile file) {
        System.out.println(file.getContentType());
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

}
