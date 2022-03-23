/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.servicios;

import com.tucuman.notas.entidades.Foto;
import com.tucuman.notas.entidades.Usuario;
import com.tucuman.notas.enums.Rol;
import com.tucuman.notas.repositorios.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nahue
 */
@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private FotoServicio fotoServicio;

    public Usuario registrarUsuario(String id, String username, String password, String password2, MultipartFile file) throws Exception {
        
        if (username.isEmpty()) {
            throw new Exception("El username no puede estar vacío");
        }
        
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null) {
            throw new Exception("El usuario ya existe, pruebe con otro nombre");
        }
        
        if (id != null) {
            usuario = getUsuario(id);
            if (usuario == null) {
                throw new Exception("No se puede modificar el usuario, porque no existe en la base de datos");
            }
        } else {
            
            if (password.isEmpty()) {
            throw new Exception("La contraseña no puede estar vacía");
            }

            
            if (!password.equals(password2)) {
                throw new Exception("Las contraseñas ingresadas deben ser iguales");
            }

             usuario = new Usuario();
            
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            usuario.setPassword(encoder.encode(password));
            usuario.setRol(Rol.USUARIO);
        }
        
        if (file != null) {
            Foto foto = fotoServicio.guardar(file);
            usuario.setFoto(foto);
        }
        
        usuario.setUsername(username);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    public Usuario getUsuario(String id) {
        return usuarioRepository.getById(id);
    }
    
    public void agregarUsuarioALaSesion(Usuario usuario) {
        ServletRequestAttributes attributes  = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("usuario", usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepository.findByUsername(username);
            List<GrantedAuthority> pepe = new ArrayList<>();
            agregarUsuarioALaSesion(usuario);
            pepe.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
            return new User(username, usuario.getPassword(), pepe);
        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
    }

}
