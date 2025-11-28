package API

import model.CadastroRequest
import model.LoginRequest
import model.PixRequest
import model.PixResponse
import model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Endpoint {

    @POST("tostao/login")
    suspend fun login(@Body request: LoginRequest): Response<Usuario>

    @POST("tostao/cadastro")
    suspend fun cadastrar(@Body request: Usuario): Response<Usuario>

    @POST("tostao/pix")
    suspend fun enviarPix(@Body request: PixRequest): Response<PixResponse>
}