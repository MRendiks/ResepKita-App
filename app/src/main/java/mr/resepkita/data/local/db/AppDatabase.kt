package mr.resepkita.data.local.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import mr.resepkita.data.entitas.BahanEntity
import mr.resepkita.utils.converters


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BahanEntity::class], version = 2)
@TypeConverters(converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun Bahan(): IBahanDao //all your DAO will be added here    companion object {


    // Here we are creating singleton so it will give same      object every time instead of creating new.
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val DB_NAME: String="ResepKita" //Name of your database

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase::class.java, DB_NAME
                        ).allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE
        }
    }

}
