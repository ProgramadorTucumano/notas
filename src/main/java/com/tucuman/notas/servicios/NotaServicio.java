/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.servicios;

import com.tucuman.notas.entidades.Nota;
import com.tucuman.notas.repositorios.NotaRepository;
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
    
    public Nota guardaNota(Nota nota) throws Exception {
        if (nota.getContenido().isEmpty()) {
            throw new Exception("La nota no puede estar vacia");
        }
        return notaRepository.save(nota);
    }
    
    public List<Nota> listAll() {
        return notaRepository.findAll();
    }
}
