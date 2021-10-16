package com.talentyco.controller;

import com.talentyco.models.entity.Cliente;
import com.talentyco.models.repositories.IClienteRepository;
import com.talentyco.models.services.IClienteService;
import com.talentyco.models.services.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import java.util.Map;

/**
 * Java Title
 *
 * @author Emmanuel Nieto Muñoz
 * @version: xx/10/2021/1.0
 * @see <a href = "" />  </a>
 */

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFileService;


    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;

        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }



    @GetMapping(value = "/ver/{id}")
    public String  ver(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {

        Cliente cliente = clienteService.findOne(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        modelo.put("cliente", cliente);
        modelo.put("titulo", "Detalle cliente: " + cliente.getNombre());
        return "ver";
    }


    public String listar() {






        return "listar";
    }



    public String crear() {






        return "form";
    }




    public String editar() {






        return "listar";
    }





    public String guardar() {






        return "redirect:/listar";
    }






    public String eliminar() {






        return "redirect:/listar";
    }
}
