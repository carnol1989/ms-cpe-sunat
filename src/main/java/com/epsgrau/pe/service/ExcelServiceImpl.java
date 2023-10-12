package com.epsgrau.pe.service;

import com.epsgrau.pe.helper.ExcelHelper;
import com.epsgrau.pe.model.CajaChica;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public List<CajaChica> saveFileService(MultipartFile file) {
        List<CajaChica> cajaChicas = new ArrayList<>();
        try {
            cajaChicas = ExcelHelper.excelToCajaChicas(file.getInputStream());
            System.out.println(cajaChicas);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
        return cajaChicas;
    }

    @Override
    public ByteArrayInputStream loadFileService() {
        List<CajaChica> cajaChicas = new ArrayList<>();
        ByteArrayInputStream in = ExcelHelper.cajaChicaToExcel(cajaChicas);
        return in;
    }

    @Override
    public void loadFileTxtService(List<CajaChica> cajaChicas) throws IOException {
        File fileTxt = new File("C:\\app\\epsgrau\\cpevalidez-cajachica.txt");

        if (fileTxt.exists())
            fileTxt.delete();

        OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(fileTxt));
        BufferedWriter buffer = new BufferedWriter(output);

        for (CajaChica cajaChica: cajaChicas) {
            buffer.write(cajaChica.getNumeroDocumento() + "|");
            String tipoDocumento = cajaChica.getTipoDocumento();
            String [] arrTipoDocumento = tipoDocumento.split("-");
            buffer.write(arrTipoDocumento[0] + "|");
            buffer.write(cajaChica.getSerie() + "|");
            buffer.write(cajaChica.getNumero() + "|");
            buffer.write(cajaChica.getFecha() + "|");
            buffer.write(cajaChica.getValorTotal());
            buffer.newLine();
            buffer.flush();
        }

        buffer.close();
        output.close();
    }

    @Override
    public List<CajaChica> getAllCajaChicasService() {
        return null;
    }

}
