package com.epsgrau.pe.controller;

import com.epsgrau.pe.util.Constantes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String homepage() {
        return "redirect:/files";
    }

    @GetMapping("/caja-chica/files/new")
    public String newFileCajaChica(Model model) {
        model.addAttribute(Constantes.TITLE, Constantes.TITLE_CAJA_CHICA);
        model.addAttribute(Constantes.ACTION_UPLOAD, "/api/caja-chica/excel/upload");
        model.addAttribute(Constantes.ACTION_CREATE, "/api/caja-chica/createFileTxt");
        model.addAttribute(Constantes.ACTION_DOWNLOAD, "/api/caja-chica/downloadFileTxt");
        return "upload_form";
    }

    @GetMapping("/orden-pago/files/new")
    public String newFileOrdenPago(Model model) {
        model.addAttribute(Constantes.TITLE, Constantes.TITLE_ORDEN_PAGO);
        model.addAttribute(Constantes.ACTION_UPLOAD, "/api/orden-pago/excel/upload");
        model.addAttribute(Constantes.ACTION_CREATE, "/api/orden-pago/createFileTxt");
        model.addAttribute(Constantes.ACTION_DOWNLOAD, "/api/orden-pago/downloadFileTxt");
        return "upload_form";
    }

    @GetMapping("/recibo-honorario/files/new")
    public String newFileReciboHonorario(Model model) {
        model.addAttribute(Constantes.TITLE, Constantes.TITLE_RECIBO_HONORARIO);
        model.addAttribute(Constantes.ACTION_UPLOAD, "/api/recibo-honorario/excel/upload");
        model.addAttribute(Constantes.ACTION_CREATE, "/api/recibo-honorario/createFileTxt");
        model.addAttribute(Constantes.ACTION_DOWNLOAD, "/api/recibo-honorario/downloadFileTxt");
        return "upload_form";
    }

    @GetMapping("/registro-compra/files/new")
    public String newFileRegistroCompra(Model model) {
        model.addAttribute(Constantes.TITLE, Constantes.TITLE_REGISTRO_COMPRAS);
        model.addAttribute(Constantes.ACTION_UPLOAD, "/api/registro-compra/excel/upload");
        model.addAttribute(Constantes.ACTION_CREATE, "/api/registro-compra/createFileTxt");
        model.addAttribute(Constantes.ACTION_DOWNLOAD, "/api/registro-compra/downloadFileTxt");
        return "upload_form";
    }

    @GetMapping("/files")
    public String getListFiles(Model model) {
        return "files";
    }

}
