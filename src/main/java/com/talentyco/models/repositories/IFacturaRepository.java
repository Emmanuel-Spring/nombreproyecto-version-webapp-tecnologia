package com.talentyco.models.repositories;

import com.talentyco.models.entity.Factura;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaRepository extends CrudRepository<Factura, Long> {
}
