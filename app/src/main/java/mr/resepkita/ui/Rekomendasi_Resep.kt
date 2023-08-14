package mr.resepkita.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import mr.resepkita.MainActivity
import mr.resepkita.adapter.ResepAdapter
import mr.resepkita.data.entitas.BahanEntity
import mr.resepkita.data.local.db.AppDatabase
import mr.resepkita.databinding.ActivityRekomendasiResepBinding
import mr.resepkita.model.ResponseResep
import mr.resepkita.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Rekomendasi_Resep : AppCompatActivity(){
    private var binding: ActivityRekomendasiResepBinding? = null
    private val list = ArrayList<ResponseResep>()
    private var bahan : String = ""

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRekomendasiResepBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.rvRekomendasiResepList.setHasFixedSize(true)
        binding!!.rvRekomendasiResepList.layoutManager = LinearLayoutManager(this@Rekomendasi_Resep)

        db = AppDatabase.getDatabase(this)!!
        val bahans: List<BahanEntity> = db.Bahan().getAllBahan()
        var bahan1 = ""
        var bahan2 = ""
        var bahan3 = ""
        var bahan4 = ""
        var bahan5 = ""
        var bahan6 = ""
        val kategori = intent.getStringExtra("kategori")
        if (bahans.size == 1){
            bahan1 = bahans.get(0).nama
        }
        else if(bahans.size == 2){
            bahan1 = bahans.get(0).nama
            bahan2 = bahans.get(1).nama
        }
        else if(bahans.size == 3){
            bahan1 = bahans.get(0).nama
            bahan2 = bahans.get(1).nama
            bahan3 = bahans.get(2).nama
        }
        else if(bahans.size == 4){
            bahan1 = bahans.get(0).nama
            bahan2 = bahans.get(1).nama
            bahan3 = bahans.get(2).nama
            bahan4 = bahans.get(3).nama
        }
        else if(bahans.size == 5){
            bahan1 = bahans.get(0).nama
            bahan2 = bahans.get(1).nama
            bahan3 = bahans.get(2).nama
            bahan4 = bahans.get(3).nama
            bahan5 = bahans.get(4).nama
        }
        else if(bahans.size == 6){
            bahan1 = bahans.get(0).nama
            bahan2 = bahans.get(1).nama
            bahan3 = bahans.get(2).nama
            bahan4 = bahans.get(3).nama
            bahan5 = bahans.get(4).nama
            bahan6 = bahans.get(5).nama
        }
        val api = RetrofitClient().instance
        api.GetResep(
            bahan1,
            bahan2,
            bahan3,
            bahan4,
            bahan5,
            bahan6,
            kategori.toString()
        ).enqueue(object : Callback<ArrayList<ResponseResep>> {
            override fun onResponse(
                call: Call<ArrayList<ResponseResep>>,
                response: Response<ArrayList<ResponseResep>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let { list.addAll(it) }
                    var adapter = ResepAdapter(this@Rekomendasi_Resep, list)
                    binding!!.rvRekomendasiResepList.adapter = adapter
                }else{
                    Toast.makeText(this@Rekomendasi_Resep,
                        "Gagal mengambil Data",
                        Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ArrayList<ResponseResep>>, t: Throwable) {
                t.printStackTrace()
                Log.e("Error", t.message.toString())
            }

        })
    }

    override fun onBackPressed() {
        startActivity(Intent(this@Rekomendasi_Resep, Bahan_List::class.java))
        finish()
    }
}

