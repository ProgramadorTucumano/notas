/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.controladores;

import com.tucuman.notas.entidades.Usuario;
import com.tucuman.notas.servicios.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nahue
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("")
    public String registro(Model modelo,
            @RequestParam(name="idUsuario", required=false) String id,
            RedirectAttributes redirectAttributes) {
        try {
            if (id != null) {
                Usuario usuario = usuarioServicio.getUsuario(id);
                modelo.addAttribute("id", usuario.getId());
                System.out.println("id: "+id);
                modelo.addAttribute("username", usuario.getUsername());
             } else {
                 modelo.addAttribute("username", "");
                 modelo.addAttribute("password", "");
                 modelo.addAttribute("password2", "");
             }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo encontrar el usuario");
            return "redirect:/";
        }
        return "usuario-formulario";
    }

    @PostMapping("/registro")
    public String registroUsuario(
            @RequestParam("id") String id,
            @RequestParam("username") String username,
            @RequestParam(name="password", required=false) String password,
            @RequestParam(name="password2", required=false) String password2,
            @RequestParam(name="file", required=false) MultipartFile file,
            Model modelo) {
        try {
            Usuario usuario = usuarioServicio.registrarUsuario(id, username, password, password2, file);
            modelo.addAttribute("success", "Usuario "+ id != null ? "editado" : "registrado"  +" con exito");
            return id != null ? "redirect:/usuario/list" : "usuario-formulario";
        } catch (Exception ex) {
            ex.printStackTrace();
            if (id != null) {
                Usuario usuario = usuarioServicio.getUsuario(id);
                modelo.addAttribute("id", usuario.getId());
                System.out.println("id: "+id);
                modelo.addAttribute("username", usuario.getUsername());
             } else {
                 modelo.addAttribute("username", username);
                 modelo.addAttribute("password", password);
                 modelo.addAttribute("password2", password2);
             }
            
            modelo.addAttribute("error", ex.getMessage());
            return "usuario-formulario";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    @GetMapping("/lista")
    public String lista(Model modelo) {
        List<Usuario> usuarios = usuarioServicio.findAll();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario-lista";
    }

}
