package mr.resepkita

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mr.resepkita.data.entitas.BahanEntity
import mr.resepkita.data.local.db.AppDatabase
import mr.resepkita.ml.ModelMobilenetAdam3
import mr.resepkita.ml.ModelVgg
import mr.resepkita.ml.ModelVggAdam27030
import mr.resepkita.ml.ModelVggRmsprop
import mr.resepkita.onboard.ui.OnBoard
import mr.resepkita.ui.Bahan_List
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var camera: Button
    lateinit var gallery: Button
    lateinit var back: ImageView
    var imageSize = 224
    private var bahan : String = ""
    lateinit var db: AppDatabase
    private lateinit var myImage: Bitmap

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        camera = findViewById(R.id.takeAPhotoBtn_scanImage)
        gallery = findViewById<Button>(R.id.uploadAPhotoBtn_scanImage)
        back = findViewById(R.id.backIv_scanImage)
        camera.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, 3)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
                    }
                }
            }
        })
        gallery.setOnClickListener(View.OnClickListener {
            val cameraIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent, 1)
        })
        back.setOnClickListener {
            startActivity(Intent(this@MainActivity, OnBoard::class.java))
            finish()
        }
    }

    fun classifyImage(image: Bitmap?) {
        try {
            db = AppDatabase.getDatabase(this)!!
            myImage = image!!
            val model = ModelVgg.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image!!.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray
            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("bawang merah", "bawang putih", "bombai", "cabai hijau", "cabai merah", "jagung", "jahe", "kembang kol", "kentang", "kubis", "objek lain" ,"terong", "timun", "tomat", "wortel"
            )
            bahan = classes[maxPos]
            val bahan = BahanEntity(0, bahan, myImage)
            if (bahan.nama != "objek lain")
            {
                db.Bahan().insert(bahan)
                val intent = Intent(this@MainActivity, Bahan_List::class.java)
                startActivity(intent)
                finish()
                model.close()
            }
            else
            {
                Toast.makeText(this, "Gambar yang anda inputkan bukan sayuran,Harap Masukan Gambar Sayuran", Toast.LENGTH_LONG).show()
            }
            Log.e("Data ", bahan.nama)

        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                var image = data!!.extras!!["data"] as Bitmap?
                val dimension = Math.min(image!!.width, image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                classifyImage(image)
            } else {
                val dat = data!!.data
                var image: Bitmap? = null
                try {
                    image = MediaStore.Images.Media.getBitmap(this.contentResolver, dat)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
                classifyImage(image)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@MainActivity, OnBoard::class.java))
        finish()
    }
}