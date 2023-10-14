package com.epsgrau.pe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenPago {

    private String id;
    private String zona;
    private String localidad;
    private String serie;
    private String numero;
    private String fechaEmision;
    private String subtotal;
    private String impuesto;
    private String otros;
    private String total;
    private String numeroDocumentoIdentidad;
    private String proveedor;
    private String tipoDocumentoPago;
    private String anio;
    private String mes;
    private String usuario;
    private String fecUso;
    private String fecConform;
    private String usuarioConform;
    private String estadoConform;
    private String observacionConform;
    private String observacionComprobante;
    private String fecRevision;
    private String nroOrden;
    private String unidadUsuario;
    private String unidadUsuarioConform;
    private String estadoMov;
    private String usuarioMov;
    private String fecMovim;
    private String unidMov;
    private String anioRc;
    private String mesRc;
    private String fuenteFinanciamiento;
    private String subFuenteFinanciamiento;

}
