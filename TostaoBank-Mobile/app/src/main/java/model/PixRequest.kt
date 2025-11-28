package model

data class PixRequest(
    val idRemetente: Long,
    val chavePixDestino: String,
    val valor: Double,
    val descricao: String?
)