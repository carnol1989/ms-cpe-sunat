package com.epsgrau.pe.service;

import com.epsgrau.pe.model.OrdenPago;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface OrdenPagoExcelService {

    public List<OrdenPago> saveFileService(MultipartFile file);

    public ByteArrayInputStream loadFileService();

    public void loadFileTxtService(List<OrdenPago> ordenes) throws IOException;

}
