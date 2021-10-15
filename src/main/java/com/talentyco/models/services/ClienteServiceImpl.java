package com.talentyco.models.services;

import com.talentyco.models.entity.Cliente;
import com.talentyco.models.entity.Factura;
import com.talentyco.models.entity.Producto;
import com.talentyco.models.repositories.IClienteRepository;
import com.talentyco.models.repositories.IFacturaRepository;
import com.talentyco.models.repositories.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Java Title
 *
 * @author Emmanuel Nieto Muñoz
 * @version: xx/10/2021/1.0
 * @see <a href = "" />  </a>
 */

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IFacturaRepository facturaRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void save(Cliente cliente) {

    }

    @Override
    public Cliente findOne(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Producto> findByNombre(String term) {
        return null;
    }

    @Override
    public void saveFactura(Factura factura) {

    }

    @Override
    public Producto findProductoById(Long id) {
        return null;
    }

    @Override
    public Factura findFacturaById(Long id) {
        return null;
    }

    @Override
    public void deleteFactura(Long id) {

    }
}
