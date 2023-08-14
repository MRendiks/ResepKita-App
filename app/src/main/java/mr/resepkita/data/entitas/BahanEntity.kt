package mr.resepkita.data.entitas

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob


@Entity(tableName = "bahan")
data class BahanEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val nama: String,
        val image: Bitmap,
)