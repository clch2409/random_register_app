package pe.com.register.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PayloadUsuario {
    @SerializedName("nombreUsuario")
    @Expose
    private var username = ""
    @SerializedName("password")
    @Expose
    private var password = ""
    constructor()
    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }


}