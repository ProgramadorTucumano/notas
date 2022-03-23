/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.servicios;

import com.tucuman.notas.entidades.Foto;
import com.tucuman.notas.repositorios.FotoRepositorio;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nahue
 */
@Service
public class FotoServicio {
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    public Foto guardar(MultipartFile archivo) throws Exception {
        if (archivo != null) {
                try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setName(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch (IOException ex) {
                throw new Exception("No se pudo guardar la foto");
            }
        } else {
            return null;
        }
    }
}
