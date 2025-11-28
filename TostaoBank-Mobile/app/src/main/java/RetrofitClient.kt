import API.Endpoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://tostao-bank.onrender.com/"

    val api: Endpoint by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // 👈 JSON primeiro
            .addConverterFactory(ScalarsConverterFactory.create()) // 👈 String depois
            .build()

        retrofit.create(Endpoint::class.java)
    }
}