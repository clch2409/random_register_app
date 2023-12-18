package com.moviles.app.run.service;

import com.moviles.app.run.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<UsuarioEntity> findAll();
    Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
    UsuarioEntity add(UsuarioEntity u);
    UsuarioEntity update(UsuarioEntity u);
    UsuarioEntity delete(Long id);
}
