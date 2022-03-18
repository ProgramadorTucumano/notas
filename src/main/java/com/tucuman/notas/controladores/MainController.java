/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.controladores;

import com.tucuman.notas.servicios.MailService;
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
    
    @Autowired
    private MailService mailService;
    
    @GetMapping("")
    public String index(Model modelo) {
        return "index";
    }
    
    @GetMapping("/enviarmail")
    public String enviarMail(Model modelo) {
        mailService.enviarEmailSimple("programacion.tucuman@gmail.com", "Saludos", "Hola soy un mail enviado desde un proyecto con spring boot");
        modelo.addAttribute("mail", "se esta procesando el envio de mail");
        return "index";
    }
}
