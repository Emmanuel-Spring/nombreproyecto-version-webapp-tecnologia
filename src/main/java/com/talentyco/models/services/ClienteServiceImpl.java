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


    //  List<Cliente> findAll()
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    //  Page<Cliente> findAll(Pageable pageable)
    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    //   save(Cliente cliente)
    @Override
    @Transactional
    public void save(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    // Cliente findOne(Long id)
    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }


    // delete cliente
    @Override
    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    // List<Producto> findByNombre
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String term) {
        return productoRepository.findByNombreLikeIgnoreCase("%" + term + "%");
    }

    //  saveFactura
    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        facturaRepository.save(factura);
    }

    //  Producto findProductoById
    @Override
    @Transactional(readOnly = true)
    public Producto findProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    //  Factura findFacturaById
    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaById(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }


    // deleteFactura
    @Override
    @Transactional
    public void deleteFactura(Long id) {
        facturaRepository.deleteById(id);
    }
}
