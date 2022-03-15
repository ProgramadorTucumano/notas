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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String formulario(Model modelo) {
        modelo.addAttribute("nota", new Nota());
        return "nota-formulario";
    }
    
    @GetMapping("/modificar")
    public String formulario(@RequestParam(name = "notaId", required = true) String id, Model modelo) {
        Nota nota = notaServicio.buscarNotaPorId(id);
        modelo.addAttribute("nota", nota);
        return "nota-formulario";
    }
    
    @PostMapping("/save")
    public String formularioData(@RequestParam("id") String id,
                                 @RequestParam("contenido") String cotenido, 
                                 @RequestParam("fecha") String fecha,
                                 @RequestParam("status") String status,
                                    Model modelo) {
        try {
            Nota nota = new Nota();
            nota.setId(id);
            nota.setContenido(cotenido);
            nota.setStatus(Status.valueOf(status));
            
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-mm-dd");
            nota.setFecha(parseador.parse(fecha));
            
            notaServicio.guardaNota(nota);
            modelo.addAttribute("nota", nota);
            String success = id != null && !id.isEmpty() ? "nota modificada con exito" : "nota creada con exito";
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
