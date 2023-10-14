package com.epsgrau.pe.controller;

import com.epsgrau.pe.model.TipoComprobantePago;
import com.epsgrau.pe.service.TipoComprobantePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-comprobante-pago")
public class TipoComprobantePagoController {

    @Autowired
    private TipoComprobantePagoService tipoComprobantePagoService;

    @GetMapping
    public List<TipoComprobantePago> getAllTipoComprobantePago() {
        return tipoComprobantePagoService.findAllService();
    }

    @GetMapping("/{id}")
    public TipoComprobantePago getFindByIdTipoComprobantePago(@PathVariable Long id) {
        return tipoComprobantePagoService.findByIdService(id).get();
    }

    @GetMapping("/descripcion")
    public TipoComprobantePago getFindByDescripcionTipoComprobantePago(@RequestParam String buscar) {
        return tipoComprobantePagoService.findByDescripcionService(buscar);
    }

}
