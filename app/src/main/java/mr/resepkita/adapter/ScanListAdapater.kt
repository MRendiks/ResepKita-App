package mr.resepkita.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mr.resepkita.R
import mr.resepkita.data.entitas.BahanEntity
import mr.resepkita.data.local.db.AppDatabase
import mr.resepkita.data.local.db.IBahanDao
import mr.resepkita.databinding.ItemScanBahanBinding
import mr.resepkita.ui.Bahan_List


class ScanListAdapater() :
    RecyclerView.Adapter<ScanListAdapater.ViewHolder>() {
    var listitem = mutableListOf<BahanEntity>()
//    var removeListener: ((BahanEntity) -> Unit)? = null
//    lateinit var db: AppDatabase

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_scan_bahan, viewGroup, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var data = listitem.get(i)
        Glide.with(viewHolder.imageBahan.context)
            .asBitmap()
            .load(data.image)
            .into(viewHolder.imageBahan)

        viewHolder.txt_nama_bahan.text= data.nama

//        viewHolder.delete_btn.setOnClickListener {
//
//            CoroutineScope(Dispatchers.IO).launch {
//                db.Bahan().delete(data)
//            }
//        }
    }

    override fun getItemCount(): Int {
        return listitem.size
    }

    fun Updatedata(listData:List<BahanEntity>){
        listitem.clear()
        listitem.addAll(listData)
        notifyDataSetChanged()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageBahan: ImageView
        var txt_nama_bahan:TextView
//        var delete_btn:ImageView

        init {
            imageBahan = view.findViewById<ImageView>(R.id.ImgGambarBahan)
            txt_nama_bahan = view.findViewById<TextView>(R.id.tvNamaBahan)
//            delete_btn = view.findViewById(R.id.btn_delete)

        }
    }

}
