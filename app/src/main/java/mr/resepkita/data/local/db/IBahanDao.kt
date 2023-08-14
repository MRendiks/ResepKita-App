package mr.resepkita.data.local.db

import androidx.room.*
import mr.resepkita.data.entitas.BahanEntity

data class NamaBahan(
    @ColumnInfo(name = "nama_bahan") val nama_bahan: String?
)

@Dao
interface IBahanDao {
    @Query("SELECT * FROM bahan")
    fun getAllBahan(): List<BahanEntity>
//
//    @Query("SELECT nama FROM bahan")
//    fun getAllNamaBahan():  List<NamaBahan>

    @Insert
    fun insert(vararg bahanEntity: BahanEntity)

    @Delete
    fun delete(bahanEntity: BahanEntity)

    @Query("DELETE FROM bahan")
    fun deleteAll()

}