package com.moviles.app.run.controller;

import com.moviles.app.run.entity.CursoEntity;
import com.moviles.app.run.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoService service;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
    @GetMapping("/inactivos")
    public ResponseEntity<?> findInactives(){
        return new ResponseEntity<>(service.findAllByActivo(false), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CursoEntity c){
        return new ResponseEntity<>(service.add(c),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody CursoEntity c){
        c.setIdCurso(id);
        return new ResponseEntity<>(service.update(c),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        boolean presente = service.findById(id).isPresent();
        if(!presente){
            return new ResponseEntity<>("Curso no encontrado",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.delete(id),HttpStatus.OK);
    }
}
