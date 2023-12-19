package com.moviles.app.run.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity(name = "Usuario")
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {
    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Column(length = 30, name = "nombre_usuario")
    private String nombreUsuario;
    @Column(length = 80, name = "nombre_completo")
    private String nombreCompleto;
    @Column
    private String password;
    @Column(length = 100)
    private String email;
    @Column
    private boolean estado;

    @JsonIgnore
    public boolean validarDatos(){
        return this.nombreUsuario.length() >= 1 &&
                this.nombreCompleto.length() >= 1 &&
                this.password.length() >= 5 &&
                this.email.length() >= 5 &&
                this.email.length() <= 100;
    }
}
