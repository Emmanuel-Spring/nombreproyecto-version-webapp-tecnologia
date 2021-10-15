package com.talentyco.models.repositories;

import com.talentyco.models.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Java Title
 *
 * @author Emmanuel Nieto Muñoz
 * @version: xx/10/2021/1.0
 * @see <a href = "" />  </a>
 */

public interface IClienteRepository  extends PagingAndSortingRepository<Cliente, Long> {


}
