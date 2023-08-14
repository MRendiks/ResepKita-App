package mr.resepkita.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    private val client = OkHttpClient.Builder().build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: ApiClient by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.28.222:5000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        retrofit.create(ApiClient::class.java)
    }

}