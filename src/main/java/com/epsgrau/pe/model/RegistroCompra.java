package com.epsgrau.pe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistroCompra {

    private String fecha;
    private String codigo;
    private String serie;
    private String numero;
    private String anio;
    private String mes;
    private String numeroDocumentoIdentidad;
    private String proveedor;
    private String valorVenta;
    private String valorImpuesto;
    private String otrosMontos;
    private String total;
    private String zona;
    private String fechaRevision;
    private String nroOrden;
    private String fechaRevision2;
    private String idComprobante;
    private String nroOrdenNota;
    private String nroDetraccion;
    private String fechaDetraccion;
    private String xml;

}
