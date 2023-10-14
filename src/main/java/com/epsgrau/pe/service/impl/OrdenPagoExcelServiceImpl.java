package com.epsgrau.pe.service.impl;

import com.epsgrau.pe.helper.OrdenPagoExcelHelper;
import com.epsgrau.pe.model.OrdenPago;
import com.epsgrau.pe.model.TipoComprobantePago;
import com.epsgrau.pe.service.OrdenPagoExcelService;
import com.epsgrau.pe.service.TipoComprobantePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenPagoExcelServiceImpl implements OrdenPagoExcelService {

    @Autowired
    private TipoComprobantePagoService tipoComprobantePagoService;

    @Override
    public List<OrdenPago> saveFileService(MultipartFile file) {
        List<OrdenPago> ordenes = new ArrayList<>();
        try {
            ordenes = OrdenPagoExcelHelper.excelToOrdenesPago(file.getInputStream());
            System.out.println("Lista Ordenes Pago: " + ordenes.size());
            ordenes = deleteOrdenes(ordenes);
            System.out.println("Lista Ordenes Pago (mod): " + ordenes.size());
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
        return ordenes;
    }

    @Override
    public ByteArrayInputStream loadFileService() {
        List<OrdenPago> ordenes = new ArrayList<>();
        ByteArrayInputStream in = OrdenPagoExcelHelper.ordenesToExcel(ordenes);
        return in;
    }

    @Override
    public void loadFileTxtService(List<OrdenPago> ordenesPago) throws IOException {
        File fileTxt = new File("C:\\app\\epsgrau\\cpevalidez-ordenespago.txt");

        if (fileTxt.exists())
            fileTxt.delete();

        OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(fileTxt));
        BufferedWriter buffer = new BufferedWriter(output);

        for (OrdenPago ordenPago : ordenesPago) {
            buffer.write(ordenPago.getNumeroDocumentoIdentidad() + "|");
            TipoComprobantePago tipoComprobantePago = tipoComprobantePagoService.findByDescripcionService(ordenPago.getTipoDocumentoPago());
            buffer.write(tipoComprobantePago.getCodigo() + "|");
            buffer.write(ordenPago.getSerie() + "|");
            buffer.write(ordenPago.getNumero() + "|");
            buffer.write(ordenPago.getFechaEmision() + "|");
            buffer.write(ordenPago.getTotal());
            buffer.newLine();
            buffer.flush();
        }

        buffer.close();
        output.close();
    }

    private List<OrdenPago> deleteOrdenes(List<OrdenPago> ordenes) {
        List<OrdenPago> listResult = new ArrayList<>();
        ordenes.forEach(item -> {
            if (isTipoComprobantePagoValido(item.getTipoDocumentoPago()))
                listResult.add(item);
        });
        return listResult;
    }

    private boolean isTipoComprobantePagoValido(String tipoComprobanteBuscar) {
        boolean result = false;
        TipoComprobantePago tipoComprobantePago = tipoComprobantePagoService.findByDescripcionService(tipoComprobanteBuscar);
        if (tipoComprobanteBuscar.equals(tipoComprobantePago.getDescripcion()) &&
                (tipoComprobantePago.getCodigo().equals("01") || tipoComprobantePago.getCodigo().equals("03") ||
                        tipoComprobantePago.getCodigo().equals("07") || tipoComprobantePago.getCodigo().equals("08") ||
                        tipoComprobantePago.getCodigo().equals("R1") || tipoComprobantePago.getCodigo().equals("R7 "))) {
            result = true;
        }
        return result;
    }

}
