package com.epsgrau.pe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CajaChica {

    private String fecha;
    private String tipoDocumentoPago;
    private String serie;
    private String numero;
    private String nota;
    private String numeroDocumentoIdentidad;
    private String proveedor;
    private String valorVenta;
    private String impuesto;
    private String valorTotal;
    private String cuentaContable;
    private String centroCosto;

}
