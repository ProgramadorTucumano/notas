/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.controladores;

import com.tucuman.notas.entidades.Nota;
import com.tucuman.notas.entidades.Usuario;
import com.tucuman.notas.servicios.NotaServicio;
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
        Nota nota = new Nota();
        nota.setUsuario(new Usuario());
        modelo.addAttribute("nota", nota);
        return "nota-formulario";
    }
    
    @PostMapping("/save")
    public String formularioData(@RequestParam("contenido") String cotenido,
            @RequestParam("nombreUsuario") String nombre,
            @RequestParam("edadUsuario") Integer edad,
            Model modelo) {
        Nota nota=new Nota();
        try {
            nota.setContenido(cotenido);
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEdad(edad);
            nota.setUsuario(usuario);
            notaServicio.guardaNota(nota);
            modelo.addAttribute("nota", nota);
            return "nota-formulario";
        } catch (Exception ex) {
            ex.printStackTrace();
            modelo.addAttribute("nota", nota);
            modelo.addAttribute("error", ex.getMessage());
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
