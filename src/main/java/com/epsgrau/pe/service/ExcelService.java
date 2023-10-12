package com.epsgrau.pe.service;

import com.epsgrau.pe.model.CajaChica;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ExcelService {

    public List<CajaChica> saveFileService(MultipartFile file);

    public ByteArrayInputStream loadFileService();

    public void loadFileTxtService(List<CajaChica> cajaChicas) throws IOException;

    public List<CajaChica> getAllCajaChicasService();

}
