package com.talentyco.controller;

import com.talentyco.models.entity.Factura;
import com.talentyco.models.services.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    public String crear() {



        return "factura/form";

    }


}
