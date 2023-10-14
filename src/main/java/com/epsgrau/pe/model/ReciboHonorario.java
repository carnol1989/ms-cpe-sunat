package com.epsgrau.pe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReciboHonorario {

    private String apPaterno;
    private String apMaterno;
    private String nombres;
    private String cod;
    private String numeroDocumentoIdentidad;
    private String tipo;
    private String serie;
    private String numero;
    private String baseImponible;
    private String retencion;
    private String neto;
    private String fecEmision;
    private String fecPago;
    private String imp;
    private String documento;
    private String tipo2;
    private String proveedor;
    private String ordenAsiento;

}
