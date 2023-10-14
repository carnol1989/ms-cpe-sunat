package com.epsgrau.pe.service.impl;

import com.epsgrau.pe.model.TipoComprobantePago;
import com.epsgrau.pe.repositories.TipoComprobantePagoRepository;
import com.epsgrau.pe.service.TipoComprobantePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoComprobantePagoServiceImpl implements TipoComprobantePagoService {

    @Autowired
    private TipoComprobantePagoRepository tipoComprobantePagoRepository;

    @Override
    public void saveService(TipoComprobantePago tipoComprobantePago) {
        tipoComprobantePagoRepository.save(tipoComprobantePago);
    }

    @Override
    public void updateService(TipoComprobantePago tipoComprobantePago) {
        tipoComprobantePagoRepository.save(tipoComprobantePago);
    }

    @Override
    public void deleteByIdService(Long idTipoComprobantePago) {
        tipoComprobantePagoRepository.deleteById(idTipoComprobantePago);
    }

    @Override
    public Optional<TipoComprobantePago> findByIdService(Long idTipoComprobantePago) {
        return tipoComprobantePagoRepository.findById(idTipoComprobantePago);
    }

    @Override
    public TipoComprobantePago findByDescripcionService(String tipoComprobantePago) {
        List<TipoComprobantePago> lista = tipoComprobantePagoRepository.findByDescripcion(tipoComprobantePago);
        return (!lista.isEmpty() && lista.size() > 0) ? lista.get(0) : new TipoComprobantePago();
    }

    @Override
    public List<TipoComprobantePago> findByDescripcionAllService(String tipoComprobantePago) {
        return tipoComprobantePagoRepository.findByDescripcion(tipoComprobantePago);
    }

    @Override
    public List<TipoComprobantePago> findAllService() {
        List<TipoComprobantePago> listaTipoComprabantePago = new ArrayList<>();

        tipoComprobantePagoRepository.findAll().forEach((tpc) -> {
            listaTipoComprabantePago.add(tpc);
        });

        return listaTipoComprabantePago;
    }
}
