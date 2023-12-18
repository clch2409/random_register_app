package com.moviles.app.run.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoginDTO implements Serializable {
    private String nombreUsuario;
    private String password;
}
