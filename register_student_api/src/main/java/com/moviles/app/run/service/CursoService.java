package com.moviles.app.run.service;

import com.moviles.app.run.entity.CursoEntity;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<CursoEntity> findAll();
    List<CursoEntity> findAllByActivo(boolean activo);
    Optional<CursoEntity> findById(Long id);
    CursoEntity add(CursoEntity c);
    CursoEntity update(CursoEntity c);
    CursoEntity delete(Long id);
}
