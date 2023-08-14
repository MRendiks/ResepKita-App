package mr.resepkita.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch
import mr.resepkita.MainActivity
import mr.resepkita.R
import mr.resepkita.adapter.ScanListAdapater
import mr.resepkita.data.entitas.BahanEntity
import mr.resepkita.data.local.db.AppDatabase
import mr.resepkita.databinding.ActivityListBahanBinding
import mr.resepkita.onboard.ui.OnBoard

class Bahan_List: AppCompatActivity() {
    lateinit var db: AppDatabase
    var recyclerView: RecyclerView? = null
    var Manager: LinearLayoutManager? = null
    var adapter: ScanListAdapater? = null
    private lateinit var binding: ActivityListBahanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBahanBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        recyclerView = findViewById<View>(R.id.rv_list_bahan) as RecyclerView
        Manager = GridLayoutManager(this@Bahan_List, 2)
        recyclerView!!.layoutManager = Manager
        adapter = ScanListAdapater()
        recyclerView!!.adapter = adapter
        db = AppDatabase.getDatabase(this)!!
        GlobalScope.launch {
            displayUsers()
        }
        binding!!.fab.setOnClickListener {
            startActivity(Intent(this@Bahan_List, MainActivity::class.java))
            finish()
        }
        binding!!.rekomendasi.setOnClickListener {
            val bahans: List<BahanEntity> = db.Bahan().getAllBahan()
            if (bahans.isEmpty()){
                Toast.makeText(this, "Harap Inputkan Gambar", Toast.LENGTH_LONG).show()
            }else{
                showSpinnerDialog()
            }

        }

        binding!!.btnDelete.setOnClickListener {
            GlobalScope.launch {
                db.Bahan().deleteAll()
            }
            Toast.makeText(this, "Data Dihapus semuanya", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@Bahan_List, Bahan_List::class.java))
            finish()
        }

    }

    @SuppressLint("MissingInflatedId")
    private fun showSpinnerDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_spinner, null)

        val spinner = dialogView.findViewById<Spinner>(R.id.spinner)
        val items = arrayOf("kuah", "goreng", "rumis", "oseng", "bakar", "full sayur")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter

        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Silahkan Pilih Jenis Rekomendasi Resep Anda?")
        dialogBuilder.setPositiveButton("Pilih") { dialog, which ->
            val selectedItem = spinner.selectedItem as String
            val intent = Intent(this@Bahan_List, Rekomendasi_Resep::class.java)
            intent.putExtra("kategori", selectedItem.toString())
            startActivity(intent)
            finish()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private suspend fun displayUsers() {
        val bahans: List<BahanEntity> = db.Bahan().getAllBahan()
        runOnUiThread() {
            adapter?.Updatedata(bahans)

        }
    }
    override fun onBackPressed() {
        startActivity(Intent(this@Bahan_List, OnBoard::class.java))
        finish()
        super.onBackPressed()
    }
}