package com.epsgrau.pe.controller;

import com.epsgrau.pe.helper.ExcelHelper;
import com.epsgrau.pe.message.ResponseMessage;
import com.epsgrau.pe.model.CajaChica;
import com.epsgrau.pe.model.RegistroCompra;
import com.epsgrau.pe.service.CajaChicaExcelService;
import com.epsgrau.pe.service.RegistroCompraExcelService;
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
@RequestMapping("/api/registro-compra")
public class RegistroCompraExcelController {

    @Autowired
    RegistroCompraExcelService registroCompraExcelService;

    @PostMapping("/excel/upload")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file,
                             HttpServletRequest request) {
        String message = "";
        List<RegistroCompra> lista = new ArrayList<>();
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                lista = registroCompraExcelService.saveFileService(file);
                request.getSession().setAttribute(Constantes.LISTA_REGISTRO_COMPRAS, lista);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            }
        } else {
            message = "Please upload an excel file!";
        }

        model.addAttribute(Constantes.TITLE, Constantes.TITLE_REGISTRO_COMPRAS);
        model.addAttribute(Constantes.ACTION_UPLOAD, "/api/registro-compra/excel/upload");
        model.addAttribute(Constantes.ACTION_CREATE, "/api/registro-compra/createFileTxt");
        model.addAttribute(Constantes.ACTION_DOWNLOAD, "/api/registro-compra/downloadFileTxt");
        model.addAttribute(Constantes.MESSAGE, message);
        model.addAttribute(Constantes.LISTA_REGISTRO_COMPRAS, lista);

        return "upload_form";
    }

    @GetMapping("/createFileTxt")
    public String createFileTxt(Model model, HttpSession session) throws IOException {
        String message = "Se creo el archivo .txt en su PC";
        List<RegistroCompra> lista = (List<RegistroCompra>) session.getAttribute(Constantes.LISTA_REGISTRO_COMPRAS);
        registroCompraExcelService.loadFileTxtService(lista);
        model.addAttribute(Constantes.TITLE, Constantes.TITLE_REGISTRO_COMPRAS);
        model.addAttribute(Constantes.ACTION_UPLOAD, "/api/registro-compra/excel/upload");
        model.addAttribute(Constantes.ACTION_CREATE, "/api/registro-compra/createFileTxt");
        model.addAttribute(Constantes.ACTION_DOWNLOAD, "/api/registro-compra/downloadFileTxt");
        model.addAttribute(Constantes.MESSAGE, message);
        return "upload_form";
    }

    @GetMapping("/downloadFileTxt")
    public ResponseEntity<Resource> getFileTxt(HttpSession session) throws IOException {
        String filename = "cpevalidez-registrocompras.txt";

        byte[] content = IOUtils.toByteArray(new FileInputStream("C:\\app\\epsgrau\\cpevalidez-registrocompras.txt"));
        Resource file = new ByteArrayResource(content);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(file.contentLength())
                .body(file);
    }

}