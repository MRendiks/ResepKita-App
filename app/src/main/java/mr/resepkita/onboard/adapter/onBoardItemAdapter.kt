package mr.resepkita.onboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mr.resepkita.R
import mr.resepkita.onboard.models.OnBoardItem


class onBoardItemAdapter(private val onBoardItem: List<OnBoardItem>) :
RecyclerView.Adapter<onBoardItemAdapter.SplashScreenItemViewHolder>(){
    inner class SplashScreenItemViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private val onBoardImage = view.findViewById<ImageView>(R.id.imageSplash)
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDeskiprisi = view.findViewById<TextView>(R.id.textDeskripsi)

        fun bind(onBoardItem: OnBoardItem){
            onBoardImage.setImageResource(onBoardItem.onboardImage)
            textTitle.text = onBoardItem.title
            textDeskiprisi.text = onBoardItem.deskripsi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplashScreenItemViewHolder {
        return SplashScreenItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboard_screen_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SplashScreenItemViewHolder, position: Int) {
        holder.bind(onBoardItem[position])
    }

    override fun getItemCount(): Int {
        return onBoardItem.size
    }
}