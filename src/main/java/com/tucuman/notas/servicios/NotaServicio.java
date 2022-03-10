/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.servicios;

import com.tucuman.notas.entidades.Nota;
import com.tucuman.notas.entidades.Usuario;
import com.tucuman.notas.repositorios.NotaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nahue
 */
@Service
public class NotaServicio {
    
    @Autowired
    private NotaRepository notaRepository;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    
    public Nota guardaNota(Nota nota) throws Exception {
        if (nota.getContenido().isEmpty()) {
            throw new Exception("La nota no puede estar vacia");
        }
        if (nota.getUsuario().getNombre().isEmpty()) {
            throw new Exception("El nombre de usuario no puede ser vacío");
        }
        if (nota.getUsuario().getEdad() == null) {
            throw new Exception("La edad del usuario no puede estar vacía");
        }
        if (nota.getUsuario().getEdad() < 1) {
            throw new Exception("La edad del usuario no puede ser menor a 1");
        }
        
        Usuario usuario = usuarioServicio.buscarPorNombre(nota.getUsuario().getNombre());
        if (usuario != null) {
            nota.setUsuario(usuario);
        }
        
        return notaRepository.save(nota);
    }
    
    public List<Nota> listAll() {
        return notaRepository.findAll();
    }
}
