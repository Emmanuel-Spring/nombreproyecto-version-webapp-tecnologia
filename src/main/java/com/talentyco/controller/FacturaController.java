package com.talentyco.controller;

import com.talentyco.models.entity.Cliente;
import com.talentyco.models.entity.Factura;
import com.talentyco.models.entity.ItemFactura;
import com.talentyco.models.entity.Producto;
import com.talentyco.models.services.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Java Title
 *
 * @author Emmanuel Nieto Muñoz
 * @version: xx/10/2021/1.0
 * @see <a href = "" />  </a>
 */

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    private IClienteService clienteService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public String ver(@PathVariable(value = "id") Long id, Model modelo, RedirectAttributes flash) {

        Factura factura = clienteService.findFacturaById(id);

        if (factura == null) {
            flash.addFlashAttribute("error", "La factura no existe en la base de datos !!!!");
            return "redirect:/listar";
        }

        modelo.addAttribute("factura", factura);
        modelo.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));

        return "factura/ver";
    }


    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> modelo,
                        RedirectAttributes flash) {
        Cliente cliente = clienteService.findOne(clienteId);

        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente nom existe en la base de datos");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);

        modelo.put("factura", factura);
        modelo.put("titulo", "Crear Factura");

        return "factura/form";
    }


    @GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
    public @ResponseBody List<Producto> cargarProductos (@PathVariable String term) {
        return clienteService.findByNombre(term);
    }

    @GetMapping("/form")
    public String guardar(@Valid Factura factura,
                          BindingResult result, Model modelo,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus status){

        if (result.hasErrors()) {
            modelo.addAttribute("titulo", "Crear Factura");
            return "factura/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Producto producto = clienteService.findProductoById(itemId[i]);

            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.addItemFactura(linea);

            log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
        }

        clienteService.saveFactura(factura);
        status.setComplete();

        flash.addFlashAttribute("Success", "Factura creada con éxito !!!");

        return "redirect:/ver/" + factura.getCliente().getId();
    }


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        Factura factura = clienteService.findFacturaById(id);

        if (factura != null) {
            clienteService.deleteFactura(id);
            flash.addFlashAttribute("success", "Factura elminada con éxito !!!");
            return "redirect:/ver/" + factura.getCliente().getId();
        }

        flash.addFlashAttribute("error", "la factura no existe en la base de datos, no se pudo eliminasr !");

        return "redirect:/listar";
    }

}
