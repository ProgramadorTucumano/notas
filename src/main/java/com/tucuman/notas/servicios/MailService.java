/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucuman.notas.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author nahue
 */
@Service
public class MailService {
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    public void enviarEmailSimple(String destinario, String asunto, String contenido) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(destinario);
        simpleMailMessage.setSubject(asunto);
        simpleMailMessage.setText(contenido);
        javaMailSender.send(simpleMailMessage);
    }
}
