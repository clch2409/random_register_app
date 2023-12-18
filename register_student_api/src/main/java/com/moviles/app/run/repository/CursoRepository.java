package com.moviles.app.run.repository;

import com.moviles.app.run.entity.CursoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CursoRepository extends CrudRepository<CursoEntity,Long> {
    List<CursoEntity> findAllByActivo(boolean activo);
}
