package model

import com.google.gson.annotations.SerializedName

data class CadastroRequest(
    @SerializedName("nomeUsuario") val nome: String,
    @SerializedName("emailUsuario") val email: String,
    @SerializedName("senhaUsuario") val senha: String,
    @SerializedName("cpfUsuario") val cpf: String,
    @SerializedName("rendaUsuario") val renda: String,
    @SerializedName("telefoneUsuario") val telefone: String
)