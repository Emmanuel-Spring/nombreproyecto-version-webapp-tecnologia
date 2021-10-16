package com.talentyco.controller;

import com.talentyco.models.entity.Cliente;
import com.talentyco.models.services.IClienteService;
import com.talentyco.models.services.IUploadFileService;
import com.talentyco.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
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


    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {

        Pageable pageRequest = PageRequest.of(page, 4);

        Page<Cliente> clientes = clienteService.findAll(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);
        modelo.addAttribute("titulo", "Listado de clientes");
        modelo.addAttribute("Clientes", clientes);
        modelo.addAttribute("page", pageRender);
        return "listar";
    }



    @RequestMapping(value = "/form")
    public String crear(Map<String, Object> modelo) {

        Cliente cliente = new Cliente();
        modelo.put("Cliente", cliente);
        modelo.put("titulo", "Formulario de cliente");

        return "form";
    }



    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {

        Cliente cliente = null;

        if (id > 0) {
            cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", "El id del cliente no existe en la BBDD !!!!!");
                return "redirect:/listar";
            }
            } else {
                flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
                return "redirect:/listar";
            }
            modelo.put("cliente", cliente);
            modelo.put("titulo", "Editar Cliente");
            return "form";
    }






    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model modelo,
                          @RequestParam("file")MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            modelo.addAttribute("titulo", "Formulario del cliente");
            return "form";
        }

        if(!foto.isEmpty()) {
            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
            && cliente.getFoto().length() > 0){

                uploadFileService.delete(cliente.getFoto());
            }

            String uniqueFileName = null;
            try {
                uniqueFileName = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFileName + "'");

            cliente.setFoto(uniqueFileName);
        }

        String mensajeFlash = (cliente.getId() != null) ? "Cliente editando con éxito !!" : "Cliente creado con éxito !!";

        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("Success !!!!", mensajeFlash);


        return "redirect:/listar";
    }






    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);

            clienteService.delete(id);
            flash.addFlashAttribute("Success !!!!", "Cliente eliminado con éxito !!");

            if (uploadFileService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "Foto" + cliente.getFoto() + "Eliminada con éxito !!!");
            }
        }

        return "redirect:/listar";
    }
}
