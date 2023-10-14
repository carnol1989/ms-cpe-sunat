package com.epsgrau.pe.service;

import com.epsgrau.pe.model.TipoComprobantePago;

import java.util.List;
import java.util.Optional;

public interface TipoComprobantePagoService {

    void saveService(TipoComprobantePago tipoComprobantePago);

    void updateService(TipoComprobantePago tipoComprobantePago);

    void deleteByIdService(Long idTipoComprobantePago);

    Optional<TipoComprobantePago> findByIdService(Long idTipoComprobantePago);

    TipoComprobantePago findByDescripcionService(String tipoComprobantePago);

    List<TipoComprobantePago> findByDescripcionAllService(String tipoComprobantePago);

    List<TipoComprobantePago> findAllService();

}
