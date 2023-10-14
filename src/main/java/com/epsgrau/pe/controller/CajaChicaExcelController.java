package com.epsgrau.pe.controller;

import com.epsgrau.pe.helper.CajaChicaExcelHelper;
import com.epsgrau.pe.helper.ExcelHelper;
import com.epsgrau.pe.message.ResponseMessage;
import com.epsgrau.pe.model.CajaChica;
import com.epsgrau.pe.service.CajaChicaExcelService;
import com.epsgrau.pe.util.Constantes;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin("http://localhost:8081")
@Controller
@RequestMapping("/api/caja-chica")
public class CajaChicaExcelController {

    @Autowired
    CajaChicaExcelService cajaChicaExcelService;

    @PostMapping("/excel/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
                                                      HttpServletRequest request) {
        String message = "";
        List<CajaChica> cajaChicas = new ArrayList<>();
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                cajaChicas = cajaChicaExcelService.saveFileService(file);
                request.getSession().setAttribute(Constantes.LISTA_CAJA_CHICA, cajaChicas);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/createFileTxt")
    public ResponseEntity<?> createFileTxt(HttpSession session) throws IOException {
        String filename = "cpevalidez-cajachica.txt";
        List<CajaChica> cajaChicas = (List<CajaChica>) session.getAttribute(Constantes.LISTA_CAJA_CHICA);

        cajaChicaExcelService.loadFileTxtService(cajaChicas);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/downloadFileTxt")
    public ResponseEntity<Resource> getFileTxt(HttpSession session) throws IOException {
        String filename = "cpevalidez-cajachica.txt";

        byte[] content = IOUtils.toByteArray(new FileInputStream("C:\\app\\epsgrau\\cpevalidez-cajachica.txt"));
        Resource file = new ByteArrayResource(content);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(file.contentLength())
                .body(file);
    }

}