package com.moviles.app.run.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Curso")
@Table(name = "curso")
public class CursoEntity implements Serializable {
    @Id
    @Column(name = "id_curso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;
    @Column(length = 100)
    private String nombre;
    private boolean activo;

    @JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "cursos")
    private List<AlumnoEntity> alumnos;
}
