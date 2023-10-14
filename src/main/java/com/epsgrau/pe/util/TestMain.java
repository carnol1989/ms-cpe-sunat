package com.epsgrau.pe.util;

import com.epsgrau.pe.model.TipoComprobantePago;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {

    public static void main (String args[]) {
        TipoComprobantePago result = new TipoComprobantePago();
        List<TipoComprobantePago> listTipoComprobantePago = Datos.cargarListaTipoComprobatePago();
        Pattern pattern = null;
        Matcher matcher = null;
        String regex = "";
        String abrvPerm = "";
        String [] arrAbrvPermi = new String[2];
        for (TipoComprobantePago item : listTipoComprobantePago) {
            if (item.getCodigo().equals("01")) {
                abrvPerm = item.getAbreviatura();
                arrAbrvPermi = abrvPerm.split("-");
                regex = "(F[^C|^D]\\w+)|(E[^C|^D]\\w+)";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher("FF01");//FC01    FF01    FD01    |   E001    EC01    ED01
                if (matcher.find()) {
                    result = item;
                    break;
                }
            }
        }
        System.out.println(result.toString());
    }
}
