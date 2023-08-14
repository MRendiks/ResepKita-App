package mr.resepkita.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mr.resepkita.R
import mr.resepkita.model.ResponseResep
import mr.resepkita.ui.Detail.DetailResep

class ResepAdapter (mCtx : Context, val responseResep: ArrayList<ResponseResep>):
RecyclerView.Adapter<ResepViewHolder>(){
    val mCtx = mCtx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rekomendasi_resep,parent,false)
        return ResepViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResepViewHolder, position: Int) {
        val reseps: ResponseResep = responseResep[position]
        holder.btnResep.setOnClickListener {
            val intent = Intent(mCtx, DetailResep::class.java)
            intent.putExtra("id_resep", reseps.id_resep)
            intent.putExtra("nama_resep", reseps.nama_resep)
            intent.putExtra("bahan", reseps.bahan)
            intent.putExtra("langkah", reseps.langkah)
            intent.putExtra("gambar", reseps.gambar)
            mCtx.startActivity(intent)

        }
        return holder.bindView(mCtx,responseResep[position])
    }

    override fun getItemCount(): Int {
        return responseResep.size
    }
}

class ResepViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val nama_resep: TextView = itemView.findViewById(R.id.tvNamResep)
//    private val bahan: TextView = itemView.findViewById(R.id.tvBahan)
    private var gambar: ImageView = itemView.findViewById(R.id.ImgGambarResep)
    internal val btnResep: LinearLayout = itemView.findViewById(R.id.btnResep)
    private val url = "https://taresep.000webhostapp.com/assets/img/images_api/"

    fun bindView(mCtx : Context,responseResep: ResponseResep){
        nama_resep.text = responseResep.nama_resep
//        bahan.text = responseResep.bahan
        Picasso.with(mCtx).load(url+responseResep.gambar).into(gambar)

    }
}