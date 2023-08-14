package mr.resepkita.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import mr.resepkita.databinding.LoginActivityBinding
import mr.resepkita.model.ResponseLogin
import mr.resepkita.network.RetrofitClient
import mr.resepkita.ui.Bahan_List
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity: AppCompatActivity() {
    private var binding: LoginActivityBinding? = null
    private var user : String = ""
    private var pass : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnLogin.setOnClickListener{
            user = binding!!.etUsername.text.toString()
            pass = binding!!.etPassword.text.toString()

            when{
                user == "" -> {
                    binding!!.etUsername.error = "Username Tidak Boleh Kosong"
                }
                pass == "" -> {
                    binding!!.etPassword.error = "Password Tidak Boleh Kosong"
                }
                else -> {
                    binding!!.loading.visibility = View.VISIBLE
                    getData()
                }
            }
        }

        binding!!.btnRegis.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterAcitivity::class.java))
            finish()
        }

    }

    private fun getData(){
        val api = RetrofitClient().instance
        api.login(user,pass).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful){
                    if (response.body()?.response == true){
                            binding!!.loading.visibility = View.GONE
                            startActivity(Intent(this@LoginActivity, Bahan_List::class.java))
                            finish()
                        }
                    }else{
                        binding!!.loading.visibility = View.GONE
                        Toast.makeText(this@LoginActivity,
                            "Login Gagal, Periksa Kembali username dan Password",
                            Toast.LENGTH_LONG).show()
                    }
                }
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("Pesan Error", "${t.message}")
            }


        })
    }
}