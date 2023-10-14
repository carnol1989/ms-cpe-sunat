package com.epsgrau.pe.service.impl;

import com.epsgrau.pe.helper.CajaChicaExcelHelper;
import com.epsgrau.pe.helper.RegistroCompraExcelHelper;
import com.epsgrau.pe.model.CajaChica;
import com.epsgrau.pe.model.RegistroCompra;
import com.epsgrau.pe.model.TipoComprobantePago;
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
public class RegistroCompraExcelServiceImpl implements RegistroCompraExcelService {

    @Autowired
    private TipoComprobantePagoService tipoComprobantePagoService;

    @Override
    public List<RegistroCompra> saveFileService(MultipartFile file) {
        List<RegistroCompra> lista = new ArrayList<>();
        try {
            lista = RegistroCompraExcelHelper.excelToRegistroCompras(file.getInputStream());
            System.out.println("Lista Registro Compras: " + lista.size());
            lista = deleteRegistroCompra(lista);
            System.out.println("Lista Registro Compras (mod): " + lista.size());
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public ByteArrayInputStream loadFileService() {
        List<RegistroCompra> lista = new ArrayList<>();
        ByteArrayInputStream in = RegistroCompraExcelHelper.registroComprasToExcel(lista);
        return in;
    }

    @Override
    public void loadFileTxtService(List<RegistroCompra> lista) throws IOException {
        File fileTxt = new File("C:\\app\\epsgrau\\cpevalidez-registrocompras.txt");

        if (fileTxt.exists())
            fileTxt.delete();

        OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(fileTxt));
        BufferedWriter buffer = new BufferedWriter(output);

        for (RegistroCompra item : lista) {
            buffer.write(item.getNumeroDocumentoIdentidad() + "|");
            String serie = item.getSerie();
            TipoComprobantePago tipoComprobantePago = obtenerTipoComprobantePago(serie);
            buffer.write(tipoComprobantePago.getCodigo() + "|");
            buffer.write(item.getSerie() + "|");
            buffer.write(item.getNumero() + "|");
            buffer.write(item.getFecha() + "|");
            buffer.write(item.getTotal());
            buffer.newLine();
            buffer.flush();
        }

        buffer.close();
        output.close();
    }

    private List<RegistroCompra> deleteRegistroCompra(List<RegistroCompra> lista) {
        List<RegistroCompra> listResult = new ArrayList<>();
        lista.forEach(item -> {
            TipoComprobantePago tipoComprobantePago = obtenerTipoComprobantePago(item.getSerie());
            if (tipoComprobantePago != null)
                listResult.add(item);
        });
        return listResult;
    }

    private TipoComprobantePago obtenerTipoComprobantePago(String serie) {
        TipoComprobantePago result = null;
        List<TipoComprobantePago> listTipoComprobantePago = tipoComprobantePagoService.findAllService();
        Pattern pattern = null;
        Matcher matcher = null;
        String regex = "";
        String abrvPerm = "";
        for (TipoComprobantePago item : listTipoComprobantePago) {
            if (item.getCodigo().equals("01")) {
                regex = "(F[^C|^D]\\w+)|(E[^C|^D]\\w+)";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(serie);
                if (matcher.find()) {
                    result = item;
                    break;
                }
            } else if (item.getCodigo().equals("03") || item.getCodigo().equals("07") ||
                    item.getCodigo().equals("08")) {
                abrvPerm = item.getAbreviatura();
                if (serie.startsWith(abrvPerm)) {
                    result = item;
                    break;
                }
            }
        }
        return result;
    }

}
