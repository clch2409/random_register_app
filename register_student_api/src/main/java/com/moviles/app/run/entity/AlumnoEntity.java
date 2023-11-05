package com.moviles.app.run.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Alumno")
@Table(name = "alumno")
public class AlumnoEntity implements Serializable {
    @Id
    @Column(name = "id_alumno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlumno;
    @Column(length = 60)
    private String nombres;
    @Column(length = 60)
    private String apellidos;
    @Column(length = 8)
    private String dni;
    @Column(length = 15)
    private String telefono;
    @Column(length = 15)
    private String genero; //Radio
    private boolean activo; //Checkbox

    @JsonIgnore
    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "alumno_curso",
            joinColumns = @JoinColumn(name = "id_alumno"),
            inverseJoinColumns = @JoinColumn(name = "id_curso"))
    private List<CursoEntity> cursos;
}
