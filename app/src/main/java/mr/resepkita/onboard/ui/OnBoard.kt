package mr.resepkita.onboard.ui

import android.app.ActionBar.LayoutParams
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mr.resepkita.MainActivity
import mr.resepkita.R
import mr.resepkita.data.local.db.AppDatabase
import mr.resepkita.onboard.adapter.onBoardItemAdapter
import mr.resepkita.onboard.models.OnBoardItem
import mr.resepkita.ui.Bahan_List
import mr.resepkita.ui.auth.LoginActivity

class OnBoard : AppCompatActivity(){
    private lateinit var onBoardItemAdapter: onBoardItemAdapter
    private lateinit var indicatorsContainer: LinearLayout
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboard_screen)
        setSplashScreenItem()
        setupIndicators()
        setCurrentIndicator(0)

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Silahkan Konfirmasi .")
            setMessage("Apakah Kamu ingin keluar dari aplikasi?")

            setPositiveButton("Yes") { _, _ ->
                super.onBackPressed()
            }

            setNegativeButton("No"){_, _ ->
                Toast.makeText(this@OnBoard, "Terima Kasih",
                    Toast.LENGTH_LONG).show()
            }
            setCancelable(true)
        }.create().show()
    }

    private fun setSplashScreenItem(){
        onBoardItemAdapter = onBoardItemAdapter(
            listOf(
                OnBoardItem(
                    onboardImage = R.drawable.splash1,
                    "Pindai Bahan Sayuranmu",
                "Kamu dapat memindai bahan sayuranmu untuk mendapatkan rekomendasi resep"
                ),
                OnBoardItem(
                    onboardImage = R.drawable.splash2,
                    "Bisa memindai lebih dari 1 bahan",
                    "Pada Aplikasi ini kamu bisa memindai lebih dari 1 bahan, jadi jangan khawatir"
                ),
                OnBoardItem(
                    onboardImage = R.drawable.splash3,
                    "Bisa mengupload gambar yang telah kamu punya",
                    "Jika tidak ingin memindai, tenang saja. kamu bisa mengupload gambar untuk rekomendasi"
                )
            )
        )
        val splashViewPager = findViewById<ViewPager2>(R.id.spalshViewPager)
        splashViewPager.adapter = onBoardItemAdapter
        splashViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
            })
        (splashViewPager.getChildAt(0)as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.nextImg).setOnClickListener{
            if (splashViewPager.currentItem+1 < onBoardItemAdapter.itemCount){
                splashViewPager.currentItem += 1
            }else{
                navigateToLoginAcitivity()
            }
        }

        findViewById<TextView>(R.id.textSkip).setOnClickListener{
            navigateToLoginAcitivity()
        }
        findViewById<MaterialButton>(R.id.buttonMulai).setOnClickListener{
            navigateToLoginAcitivity()
        }
    }

    private fun setupIndicators(){
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onBoardItemAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams= LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(6,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun navigateToLoginAcitivity(){
        db = AppDatabase.getDatabase(this)!!
        GlobalScope.launch {
            db.Bahan().deleteAll()
        }

        startActivity(Intent(this@OnBoard, LoginActivity::class.java))
        finish()
    }

    private fun setCurrentIndicator(position:Int){
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}