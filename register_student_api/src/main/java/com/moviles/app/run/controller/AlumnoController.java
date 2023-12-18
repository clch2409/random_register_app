package com.moviles.app.run.controller;

import com.moviles.app.run.entity.AlumnoEntity;
import com.moviles.app.run.entity.CursoEntity;
import com.moviles.app.run.service.AlumnoService;
import com.moviles.app.run.service.CursoService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {
    @Autowired
    private AlumnoService service;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/activos")
    public ResponseEntity<?> findActives(){
        return new ResponseEntity<>(service.findAllByActivo(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody AlumnoEntity a, @RequestParam("id_curso") Long idCurso){
        List<CursoEntity> listaCursos = new ArrayList<>();
        boolean cursoPresente = cursoService.findById(idCurso).isPresent();
        if(cursoPresente){
            listaCursos.add(cursoService.findById(idCurso).get());
            a.setCursos(listaCursos);
            return new ResponseEntity<>(service.add(a),HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Curso a asignar no encontrado.",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody AlumnoEntity a){
        a.setIdAlumno(id);
        return new ResponseEntity<>(service.update(a),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        boolean presente = service.findById(id).isPresent();
        if(!presente){
            return new ResponseEntity<>("Alumno no encontrado.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.delete(id),HttpStatus.OK);
    }
}
