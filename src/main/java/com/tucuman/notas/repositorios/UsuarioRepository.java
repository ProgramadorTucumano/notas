/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.repositorios;

import com.tucuman.notas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nahue
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    
    @Query("select u from Usuario u where u.nombre = :pepe")
    public Usuario buscarUsuarioPorNombre(@Param("pepe") String nombre);
}
