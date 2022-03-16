/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.controladores;

import com.tucuman.notas.servicios.NotaServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nahue
 */
@Controller
@RequestMapping("")
public class MainController {
    
    @Autowired
    private NotaServicio notaServicio;
    
    @GetMapping("")
    public String index(Model modelo) {
        return "index";
    }
    
    @GetMapping("/elegirruta")
    public String index(@RequestParam("ruta") String ruta, Model modelo,
                                RedirectAttributes redirectAttributes) {
        switch (ruta.toLowerCase()) {
            case "nota":
                ruta = "/nota";
                break;
            case "usuario":
                ruta = "/usuario";
                break;
            default:
        redirectAttributes.addFlashAttribute("error", "No tenemos un recursos para esa busqueda");
                ruta = "/";
        }
        return "redirect:"+ruta;
    }
}
