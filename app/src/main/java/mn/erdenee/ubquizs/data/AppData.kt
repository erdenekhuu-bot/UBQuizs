package mn.erdenee.ubquizs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class], version = 1)
abstract class AppData: RoomDatabase() {
    abstract fun categoryDao(): DAO

    companion object {
        private var INSTANCE: AppData ?=null

        fun getDatabase(context: Context): AppData {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppData::class.java,
                    "ubquiz_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}