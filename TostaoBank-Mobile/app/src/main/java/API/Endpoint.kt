package API

import retrofit2.http.GET

interface Endpoint {
    @GET("teste")
    suspend fun testarAPI(): String
}