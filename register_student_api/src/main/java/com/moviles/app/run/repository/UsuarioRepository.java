package com.moviles.app.run.repository;

import com.moviles.app.run.entity.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<UsuarioEntity,Long> {
    Optional<UsuarioEntity> findBynombreUsuario(String nombreUsuario);
}
