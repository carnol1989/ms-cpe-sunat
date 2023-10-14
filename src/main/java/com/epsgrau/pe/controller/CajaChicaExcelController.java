package com.epsgrau.pe.controller;

import com.epsgrau.pe.helper.ExcelHelper;
import com.epsgrau.pe.model.CajaChica;
import com.epsgrau.pe.service.CajaChicaExcelService;
import com.epsgrau.pe.util.Constantes;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file,
                             HttpServletRequest request) {
        String message = "";
        List<CajaChica> cajaChicas = new ArrayList<>();
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                cajaChicas = cajaChicaExcelService.saveFileService(file);
                request.getSession().setAttribute(Constantes.LISTA_CAJA_CHICA, cajaChicas);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            }
        } else {
            message = "Please upload an excel file!";
        }

        model.addAttribute(Constantes.TITLE, Constantes.TITLE_CAJA_CHICA);
        model.addAttribute(Constantes.ACTION_UPLOAD, "/api/caja-chica/excel/upload");
        model.addAttribute(Constantes.ACTION_CREATE, "/api/caja-chica/createFileTxt");
        model.addAttribute(Constantes.ACTION_DOWNLOAD, "/api/caja-chica/downloadFileTxt");
        model.addAttribute(Constantes.MESSAGE, message);
        model.addAttribute(Constantes.LISTA_CAJA_CHICA, cajaChicas);

        return "upload_form";
    }

    @GetMapping("/createFileTxt")
    public String createFileTxt(Model model, HttpSession session) throws IOException {
        String message = "Se creo el archivo .txt en su PC";
        List<CajaChica> cajaChicas = (List<CajaChica>) session.getAttribute(Constantes.LISTA_CAJA_CHICA);
        cajaChicaExcelService.loadFileTxtService(cajaChicas);
        model.addAttribute(Constantes.TITLE, Constantes.TITLE_CAJA_CHICA);
        model.addAttribute(Constantes.ACTION_UPLOAD, "/api/caja-chica/excel/upload");
        model.addAttribute(Constantes.ACTION_CREATE, "/api/caja-chica/createFileTxt");
        model.addAttribute(Constantes.ACTION_DOWNLOAD, "/api/caja-chica/downloadFileTxt");
        model.addAttribute(Constantes.MESSAGE, message);
        return "upload_form";
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