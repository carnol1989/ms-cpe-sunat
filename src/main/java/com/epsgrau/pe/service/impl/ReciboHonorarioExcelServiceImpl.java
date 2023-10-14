package com.epsgrau.pe.service.impl;

import com.epsgrau.pe.helper.ReciboHonorarioExcelHelper;
import com.epsgrau.pe.helper.RegistroCompraExcelHelper;
import com.epsgrau.pe.model.ReciboHonorario;
import com.epsgrau.pe.model.RegistroCompra;
import com.epsgrau.pe.model.TipoComprobantePago;
import com.epsgrau.pe.service.ReciboHonorarioExcelService;
import com.epsgrau.pe.service.RegistroCompraExcelService;
import com.epsgrau.pe.service.TipoComprobantePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReciboHonorarioExcelServiceImpl implements ReciboHonorarioExcelService {

    @Autowired
    private TipoComprobantePagoService tipoComprobantePagoService;

    @Override
    public List<ReciboHonorario> saveFileService(MultipartFile file) {
        List<ReciboHonorario> lista = new ArrayList<>();
        try {
            lista = ReciboHonorarioExcelHelper.excelToReciboHonorarios(file.getInputStream());
            System.out.println("Lista Recibo x Honorarios: " + lista.size());
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public ByteArrayInputStream loadFileService() {
        List<ReciboHonorario> lista = new ArrayList<>();
        ByteArrayInputStream in = ReciboHonorarioExcelHelper.reciboHonorariosToExcel(lista);
        return in;
    }

    @Override
    public void loadFileTxtService(List<ReciboHonorario> lista) throws IOException {
        File fileTxt = new File("C:\\app\\epsgrau\\cpevalidez-recibohonorarios.txt");

        if (fileTxt.exists())
            fileTxt.delete();

        OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(fileTxt));
        BufferedWriter buffer = new BufferedWriter(output);

        String total = "";
        for (ReciboHonorario item : lista) {
            buffer.write(item.getNumeroDocumentoIdentidad() + "|");
            String serie = item.getSerie();
            TipoComprobantePago tipoComprobantePago = tipoComprobantePagoService.findByIdService(5L).get();
            buffer.write(tipoComprobantePago.getCodigo() + "|");
            buffer.write(item.getSerie() + "|");
            buffer.write(item.getNumero() + "|");
            buffer.write(item.getFecEmision() + "|");
            double retencion = Double.parseDouble(item.getRetencion());
            if (retencion > 0) {
                total = item.getBaseImponible();
            } else {
                total = item.getNeto();
            }
            buffer.write(total);
            buffer.newLine();
            buffer.flush();
        }

        buffer.close();
        output.close();
    }

}
