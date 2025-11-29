package model

import java.time.ZonedDateTime

data class Historico(
    val idTransferencia: Long,
    val emailTransferencia: String?,
    val valorTransferencia: Double?,
    val tipoTransferencia: String?,
    val emailRecebeTransferencia: String?,
    val dataTransferencia: String?,
    val descTransferencia: String?,
    val telefoneTransferencia: String?
)