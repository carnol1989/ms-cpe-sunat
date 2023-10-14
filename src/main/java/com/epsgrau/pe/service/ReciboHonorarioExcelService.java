package com.epsgrau.pe.service;

import com.epsgrau.pe.model.ReciboHonorario;
import com.epsgrau.pe.model.RegistroCompra;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ReciboHonorarioExcelService {

    public List<ReciboHonorario> saveFileService(MultipartFile file);

    public ByteArrayInputStream loadFileService();

    public void loadFileTxtService(List<ReciboHonorario> lista) throws IOException;

}
