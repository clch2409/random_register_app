package com.moviles.app.run.controller;

import com.moviles.app.run.entity.UsuarioEntity;
import com.moviles.app.run.entity.dto.LoginDTO;
import com.moviles.app.run.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/sq")
public class SeguridadController {
    @Autowired
    private UsuarioService service;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/iniciar_sesion")
    public ResponseEntity<?> iniciarSesion(@RequestBody LoginDTO carga){
        if(carga.getNombreUsuario().length() <= 0 || carga.getPassword().length() <= 0){
            return new ResponseEntity<>("Error! Datos de usuario incorrectos.", HttpStatus.BAD_REQUEST);
        }
        Optional<UsuarioEntity> usuario = service.findByNombreUsuario(carga.getNombreUsuario());
        if(usuario.isPresent()){
            boolean login = bCryptPasswordEncoder.matches(carga.getPassword(),usuario.get().getPassword());
            if(login){
                return new ResponseEntity<>(usuario,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Error! Acceso no permitido",HttpStatus.FORBIDDEN);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioEntity carga){
        boolean datosValidos = carga.validarDatos();
        if(!datosValidos){
            return new ResponseEntity<>("Ups! Datos de formato correcto ingresados.",HttpStatus.BAD_REQUEST);
        }
        carga.setEstado(true);
        carga.setPassword(bCryptPasswordEncoder.encode(carga.getPassword()));
        return new ResponseEntity<>(service.add(carga),HttpStatus.CREATED);
    }
}
