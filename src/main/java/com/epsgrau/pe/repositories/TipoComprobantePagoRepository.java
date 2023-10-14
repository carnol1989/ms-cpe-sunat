package com.epsgrau.pe.repositories;

import com.epsgrau.pe.model.TipoComprobantePago;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TipoComprobantePagoRepository extends CrudRepository<TipoComprobantePago, Long> {

    List<TipoComprobantePago> findByDescripcion(String descripcion);

}
