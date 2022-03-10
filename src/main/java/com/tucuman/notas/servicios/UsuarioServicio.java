/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.servicios;

import com.tucuman.notas.entidades.Usuario;
import com.tucuman.notas.repositorios.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nahue
 */
@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }
    
    public Usuario buscarPorNombre(String nombre) {
        return usuarioRepository.buscarUsuarioPorNombre(nombre);
    }
}
