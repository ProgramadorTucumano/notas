/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.controladores;

import com.tucuman.notas.entidades.Nota;
import com.tucuman.notas.enums.Status;
import com.tucuman.notas.servicios.NotaServicio;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nahue
 */
@Controller
@RequestMapping("/nota")
public class NotaController {
    
    @Autowired
    private NotaServicio notaServicio;
    
    @GetMapping("")
    public String formulario(Model modelo, @RequestParam(name = "notaId", required = false) String id) {
        Nota nota = null;
        if (id != null) {
            nota = notaServicio.buscarNotaPorId(id);
        } else {
            nota = new Nota();
        }
        modelo.addAttribute("nota", nota);
        return "nota-formulario";
    }
    
    @PostMapping("/save")
    public String formularioData( Model modelo, HttpSession session,
                                    @ModelAttribute("nota") Nota nota) {
        try {
            notaServicio.guardaNota(nota);
            List<Nota> notas = (List<Nota>) session.getAttribute("notas");
            if (notas == null) {
                notas = new ArrayList<>();
            }
            notas.add(nota);
            session.setAttribute("notas", notas);
            modelo.addAttribute("nota", nota);
            String success = nota.getId() != null && !nota.getId().isEmpty() ? "nota modificada con exito" : "nota creada con exito";
            modelo.addAttribute("success", success);
            return "nota-formulario";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "nota-formulario";
        }
    }
    
    @GetMapping("/list")
    public String listAll(Model modelo) {
        List<Nota> notas = notaServicio.listAll();
        modelo.addAttribute("listaDeNotas", notas);
        return "nota-list";
    }
}
