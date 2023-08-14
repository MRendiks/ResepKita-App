package mr.resepkita.network

import mr.resepkita.data.entitas.BahanEntity
import mr.resepkita.model.ResponseLogin
import mr.resepkita.model.ResponseRegistrasi
import mr.resepkita.model.ResponseResep
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiClient {
    @FormUrlEncoded
    @POST("get_resep")
    fun GetResep(
        @Field("bahan1") bahan1: String?,
        @Field("bahan2") bahan2: String?,
        @Field("bahan3") bahan3: String?,
        @Field("bahan4") bahan4: String?,
        @Field("bahan5") bahan5: String?,
        @Field("bahan6") bahan6: String?,
        @Field("kategori") kategori: String?
    ): Call<ArrayList<ResponseResep>>

    //POST
    @FormUrlEncoded
    @POST("login_service")
    fun login(
        @Field("username") username : String,
        @Field("password") password : String
    ):Call<ResponseLogin>

    @FormUrlEncoded
    @POST("register_service")
    fun registrasi(
        @Field("username") username : String,
        @Field("password") password : String
    ):Call<ResponseRegistrasi>
}