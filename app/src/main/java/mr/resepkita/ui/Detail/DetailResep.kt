package mr.resepkita.ui.Detail

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import mr.resepkita.databinding.ActivityDetailResepBinding
import mr.resepkita.ui.Rekomendasi_Resep

class DetailResep: AppCompatActivity() {
    private var binding : ActivityDetailResepBinding? = null
    private val url = "https://taresep.000webhostapp.com/assets/img/images_api/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailResepBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val gambar_resep : ImageView =  binding!!.imgItem
        val nama_resep : TextView = binding!!.tvCategory
        val list_bahan : TextView = binding!!.tvIngredients
        val list_langkah : TextView = binding!!.tvInstructions
        val gambar = intent.getStringExtra("gambar")

        Picasso.with(this@DetailResep).load(url+gambar).into(gambar_resep)
        nama_resep.text = intent.getStringExtra("nama_resep")
        list_bahan.text = intent.getStringExtra("bahan")
        list_langkah.text = intent.getStringExtra("langkah")

        binding!!.imgToolbarBtnBack.setOnClickListener {
            startActivity(Intent(this@DetailResep, Rekomendasi_Resep::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@DetailResep, Rekomendasi_Resep::class.java))
        finish()
        super.onBackPressed()
    }
}