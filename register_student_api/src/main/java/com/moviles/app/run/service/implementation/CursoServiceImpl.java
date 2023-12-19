package com.moviles.app.run.service.implementation;

import com.moviles.app.run.entity.CursoEntity;
import com.moviles.app.run.repository.CursoRepository;
import com.moviles.app.run.service.CursoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository repository;
    @Override
    public List<CursoEntity> findAll() {
        return (List<CursoEntity>) repository.findAll();
    }

    @Override
    public List<CursoEntity> findAllByActivo() {
        return repository.findAllByActivo();
    }

    @Override
    public Optional<CursoEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public CursoEntity add(CursoEntity c) {
        c.setIdCurso(0L);
        return repository.save(c);
    }

    @Override
    public CursoEntity update(CursoEntity c) {
        CursoEntity curso = repository.findById(c.getIdCurso()).get();
        BeanUtils.copyProperties(c,curso);
        return repository.save(curso);
    }

    @Override
    public CursoEntity delete(Long id) {
        CursoEntity curso = repository.findById(id).get();
        curso.setActivo(false);
        return repository.save(curso);
    }
}
