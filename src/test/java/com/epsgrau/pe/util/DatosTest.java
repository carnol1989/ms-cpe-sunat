package com.epsgrau.pe.util;

import com.epsgrau.pe.model.TipoComprobantePago;

import java.util.ArrayList;
import java.util.List;

public class DatosTest {

    public static TipoComprobantePago FACTURA = new TipoComprobantePago(1L, "01", "FACTURA", "F-E", "S");
    public static TipoComprobantePago BOLETA_VENTA = new TipoComprobantePago(2L, "03", "BOLETA DE VENTA", "B", "S");
    public static TipoComprobantePago NOTA_CREDITO = new TipoComprobantePago(3L, "07", "NOTA DE CRÉDITO", "FC", "S");
    public static TipoComprobantePago NOTA_DEBITO = new TipoComprobantePago(4L, "08", "NOTA DE DÉBITO", "FD", "S");
    public static TipoComprobantePago RECIBO_HONORARIOS = new TipoComprobantePago(5L, "R1", "RECIBO POR HONORARIOS", "", "S");
    public static TipoComprobantePago NOTA_CREDITO_X_RH = new TipoComprobantePago(6L, "R7", "NOTA CRÉDITO RECIBO POR HONORARIOS", "", "S");
    public static TipoComprobantePago LIQUIDACION_COMPRA = new TipoComprobantePago(7L, "04", "LIQUIDACIÓN DE COMPRA", "", "S");
    public static TipoComprobantePago POLIZA_ADJ_ELECTRONICA = new TipoComprobantePago(8L, "23", "PÓLIZA DE ADJUDICACIÓN ELECTRÓNICA", "", "S");

    public static List<TipoComprobantePago> cargarListaTipoComprobatePago() {
        List<TipoComprobantePago> lista = new ArrayList<>();
        lista.add(FACTURA);
        lista.add(BOLETA_VENTA);
        lista.add(NOTA_CREDITO);
        lista.add(NOTA_DEBITO);
        lista.add(RECIBO_HONORARIOS);
        lista.add(NOTA_CREDITO_X_RH);
        lista.add(LIQUIDACION_COMPRA);
        lista.add(POLIZA_ADJ_ELECTRONICA);
        return lista;
    }

}
