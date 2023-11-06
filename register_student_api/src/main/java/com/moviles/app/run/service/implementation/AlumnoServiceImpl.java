package com.moviles.app.run.service.implementation;

import com.moviles.app.run.entity.AlumnoEntity;
import com.moviles.app.run.repository.AlumnoRepository;
import com.moviles.app.run.service.AlumnoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImpl implements AlumnoService {
    @Autowired
    private AlumnoRepository repository;
    @Override
    public List<AlumnoEntity> findAll() {
        return (List<AlumnoEntity>) repository.findAll();
    }

    @Override
    public List<AlumnoEntity> findAllByActivo() {
        return repository.findAllByActivo();
    }

    @Override
    public Optional<AlumnoEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public AlumnoEntity add(AlumnoEntity a) {
        return repository.save(a);
    }

    @Override
    public AlumnoEntity update(AlumnoEntity a) {
        AlumnoEntity alum = repository.findById(a.getIdAlumno()).get();
        BeanUtils.copyProperties(a,alum);
        return repository.save(alum);
    }

    @Override
    public AlumnoEntity delete(Long id) {
        AlumnoEntity alum = repository.findById(id).get();
        alum.setActivo(false);
        return repository.save(alum);
    }
}
