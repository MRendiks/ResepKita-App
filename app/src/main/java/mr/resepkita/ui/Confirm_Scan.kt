package mr.resepkita.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mr.resepkita.R
import mr.resepkita.databinding.ConfirmScanBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Confirm_Scan: AppCompatActivity() {
    lateinit var  binding: ConfirmScanBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding =  ConfirmScanBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        var alertDialog = AlertDialog.Builder(this@Confirm_Scan)
            .setTitle("Warning")
            .setMessage("Apakah anda ingin scanning bahan makananan?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                startActivity(Intent(this@Confirm_Scan, Bahan_List::class.java))
                finish()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->  })
            .setIcon(R.drawable.warning_icon)
            .show()
    }
}