package com.talentyco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Java Title
 *
 * @author Emmanuel Nieto Muñoz
 * @version: xx/10/2021/1.0
 * @see <a href = "" />  </a>
 */

@Controller
public class HomeController {

    @GetMapping("")
    public String goHome() {

        return "index";
    }

}
