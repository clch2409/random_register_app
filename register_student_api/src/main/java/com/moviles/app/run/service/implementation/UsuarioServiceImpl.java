package com.moviles.app.run.service.implementation;

import com.moviles.app.run.entity.UsuarioEntity;
import com.moviles.app.run.repository.UsuarioRepository;
import com.moviles.app.run.service.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<UsuarioEntity> findAll() {
        return (List<UsuarioEntity>) repository.findAll();
    }

    @Override
    public Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario) {
        try{
            UsuarioEntity u = repository.findBynombreUsuario(nombreUsuario).get();
            return Optional.of(u);
        }
        catch(Exception ex){ return null; }
    }

    @Override
    public UsuarioEntity add(UsuarioEntity u) {
        u.setIdUsuario(0L);
        return repository.save(u);
    }

    @Override
    public UsuarioEntity update(UsuarioEntity u) {
        UsuarioEntity usuario = repository.findById(u.getIdUsuario()).get();
        BeanUtils.copyProperties(u,usuario);
        return repository.save(usuario);
    }

    @Override
    public UsuarioEntity delete(Long id) {
        UsuarioEntity usuario = repository.findById(0L).orElse(null);
        if(usuario != null){
            usuario.setEstado(false);
            return usuario;
        }
        return null;
    }
}
