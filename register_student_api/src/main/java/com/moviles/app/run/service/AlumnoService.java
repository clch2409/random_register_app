package com.moviles.app.run.service;

import com.moviles.app.run.entity.AlumnoEntity;

import java.util.List;
import java.util.Optional;

public interface AlumnoService {
    List<AlumnoEntity> findAll();
    List<AlumnoEntity> findAllByActivo();
    Optional<AlumnoEntity> findById(Long id);
    AlumnoEntity add(AlumnoEntity a);
    AlumnoEntity update(AlumnoEntity a);
    AlumnoEntity delete(Long id);
}
