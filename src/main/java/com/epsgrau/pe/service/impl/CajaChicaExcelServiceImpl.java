package com.epsgrau.pe.service.impl;

import com.epsgrau.pe.helper.CajaChicaExcelHelper;
import com.epsgrau.pe.model.CajaChica;
import com.epsgrau.pe.model.TipoComprobantePago;
import com.epsgrau.pe.service.CajaChicaExcelService;
import com.epsgrau.pe.service.TipoComprobantePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CajaChicaExcelServiceImpl implements CajaChicaExcelService {

    @Autowired
    private TipoComprobantePagoService tipoComprobantePagoService;

    @Override
    public List<CajaChica> saveFileService(MultipartFile file) {
        List<CajaChica> cajaChicas = new ArrayList<>();
        try {
            cajaChicas = CajaChicaExcelHelper.excelToCajaChicas(file.getInputStream());
            System.out.println("Lista Caja Chicas: " + cajaChicas.size());
            cajaChicas = deleteCajaChicas(cajaChicas);
            System.out.println("Lista Caja Chicas (mod): " + cajaChicas.size());
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
        return cajaChicas;
    }

    @Override
    public ByteArrayInputStream loadFileService() {
        List<CajaChica> cajaChicas = new ArrayList<>();
        ByteArrayInputStream in = CajaChicaExcelHelper.cajaChicaToExcel(cajaChicas);
        return in;
    }

    @Override
    public void loadFileTxtService(List<CajaChica> cajaChicas) throws IOException {
        File fileTxt = new File("C:\\app\\epsgrau\\cpevalidez-cajachica.txt");

        if (fileTxt.exists())
            fileTxt.delete();

        OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(fileTxt));
        BufferedWriter buffer = new BufferedWriter(output);

        for (CajaChica cajaChica : cajaChicas) {
            buffer.write(cajaChica.getNumeroDocumentoIdentidad() + "|");
            String tipoDocumentoPago = cajaChica.getTipoDocumentoPago();
            String[] arrTipoDocumentoPago = tipoDocumentoPago.split("-");
            System.out.println("arrTipoDocumentoPago[1]: " + arrTipoDocumentoPago[1]);
            TipoComprobantePago tipoComprobantePago = obtenerTipoComprobantePago(arrTipoDocumentoPago[1]);
            buffer.write(tipoComprobantePago.getCodigo() + "|");
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

    private List<CajaChica> deleteCajaChicas(List<CajaChica> cajaChicas) {
        List<CajaChica> listResult = new ArrayList<>();
        cajaChicas.forEach(cajaChica -> {
            String tipoDocumento = cajaChica.getTipoDocumentoPago();
            String[] arrTipoDocumento = tipoDocumento.split("-");
            if (isTipoComprobantePagoPermitido(arrTipoDocumento[1]))
                listResult.add(cajaChica);
        });
        return listResult;
    }

    private TipoComprobantePago obtenerTipoComprobantePago(String tipoComprobantePago) {
        TipoComprobantePago result = new TipoComprobantePago();
        result = tipoComprobantePagoService.findByDescripcionService(tipoComprobantePago);
        return result;
    }

    private boolean isTipoComprobantePagoPermitido(String tipoComprobanteCajaChica) {
        System.out.println("tipoComprobanteCajaChica: " + tipoComprobanteCajaChica);
        boolean result = false;
        List<TipoComprobantePago> listTipoComprobantePago = tipoComprobantePagoService.findAllService();
        for (TipoComprobantePago tipoComprobantePago : listTipoComprobantePago) {
            if (tipoComprobanteCajaChica.equals(tipoComprobantePago.getDescripcion()) &&
                    (tipoComprobantePago.getCodigo().equals("01") || tipoComprobantePago.getCodigo().equals("03") ||
                            tipoComprobantePago.getCodigo().equals("R1"))) {
                result = true;
                break;
            }
        }
        return result;
    }

}
