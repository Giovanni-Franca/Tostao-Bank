package API

import android.provider.ContactsContract
import model.Historico
import model.LoginRequest
import model.PixDTO
import model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface Endpoint {

    @POST("tostao/login")
    suspend fun login(@Body request: LoginRequest): Response<Usuario>

    @POST("tostao/cadastro")
    suspend fun cadastrar(@Body request: Usuario): Response<Usuario>

    @PUT("tostao/pix")
    suspend fun enviarPix(@Body body: PixDTO): Response<Usuario>

    @GET("historico/transferencias")
    suspend fun historico(@Query("email") email: String): Response<List<Historico>>
}