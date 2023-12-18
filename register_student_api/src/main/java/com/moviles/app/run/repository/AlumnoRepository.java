package com.moviles.app.run.repository;

import com.moviles.app.run.entity.AlumnoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlumnoRepository extends CrudRepository<AlumnoEntity,Long> {
    List<AlumnoEntity> findAllByActivo(boolean activo);
}
