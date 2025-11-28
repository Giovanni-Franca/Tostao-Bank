package model

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("idUsuario")
    var idUsuario: Long,

    @SerializedName("nomeUsuario")
    var nomeUsuario: String,

    @SerializedName("emailUsuario")
    var emailUsuario: String,

    @SerializedName("senhaUsuario")
    var senhaUsuario: String,

    @SerializedName("saldoUsuario")
    var saldoUsuario: String,

    @SerializedName("cartaoUsuario")
    var cartaoUsuario: String,

    @SerializedName("telefoneUsuario")
    var telefoneUsuario: String
)