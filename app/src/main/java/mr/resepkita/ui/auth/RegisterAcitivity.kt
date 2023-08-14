package mr.resepkita.ui.auth

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import mr.resepkita.R
import mr.resepkita.databinding.RegisterActivityBinding
import mr.resepkita.model.ResponseRegistrasi
import mr.resepkita.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterAcitivity: AppCompatActivity() {
    private var binding : RegisterActivityBinding? = null
    private var nama_lengkap: String= ""
    private var username : String = ""
    private var pass : String = ""
    private var jenis_kelamin : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnTambah.setOnClickListener {
            nama_lengkap = binding!!.regNamaLengkap.text.toString()
            username = binding!!.regUsername.text.toString()
            pass = binding!!.regPassword.text.toString()
            jenis_kelamin = when (binding!!.genderRadio.checkedRadioButtonId) {
                R.id.radioButton1 -> "Laki-Laki"
                R.id.radioButton2 -> "Perempuan"
                else -> "NA"
            }
            when{
                username == "" -> {
                    binding!!.regNamaLengkap.error = "Username Tidak Boleh Kosong"
                }
                nama_lengkap == "" ->{
                    binding!!.regUsername.error = "Nama Lengkap Tidak Boleh Kosong"
                }
                pass == "" -> {
                    binding!!.regPassword.error = "Password Tidak Boleh Kosong"
                }
                else -> {
                    binding!!.loading.visibility = View.VISIBLE
                    RegisData()
                }
            }
        }
        binding!!.btnCancel.setOnClickListener {
            startActivity(Intent(this@RegisterAcitivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun RegisData(){
        val api = RetrofitClient().instance
        api.registrasi(username,pass).enqueue(object : Callback<ResponseRegistrasi> {
            override fun onResponse(
                call: Call<ResponseRegistrasi>,
                response: Response<ResponseRegistrasi>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.response == true){
                        binding!!.loading.visibility = View.GONE
                        Toast.makeText(this@RegisterAcitivity,
                            "Registrasi Berhasil",
                            Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@RegisterAcitivity, LoginActivity::class.java))
                        finish()
                    }else{
                        binding!!.loading.visibility = View.GONE

                        var alertDialog = AlertDialog.Builder(this@RegisterAcitivity)
                            .setTitle("Warning")
                            .setMessage("Resgistrasi Gagal, Akun Sudah Ada")
                            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                            })
                            .setIcon(R.drawable.warning_icon)
                            .show()
                        Toast.makeText(this@RegisterAcitivity,
                            "Resgistrasi Gagal, Akun Sudah Ada",
                            Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(
                        this@RegisterAcitivity,
                        "Registrasi Gagal, Kesalahan Terjadi",
                        Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseRegistrasi>, t: Throwable) {
                Log.e("Error", "{${t.message}}")
            }
        })
    }

    override fun onBackPressed() {
        startActivity(Intent(this@RegisterAcitivity, LoginActivity::class.java))
        finish()
        super.onBackPressed()
    }
}