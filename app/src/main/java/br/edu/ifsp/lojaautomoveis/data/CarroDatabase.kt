package br.edu.ifsp.lojaautomoveis.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CarroEntity::class], version = 1)
abstract class CarroDatabase: RoomDatabase() {
    abstract fun carroDAO(): CarroDAO

    companion object {
        @Volatile
        private var INSTANCE: CarroDatabase? = null

        fun getDatabase(context: Context): CarroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarroDatabase::class.java,
                    "carroroom.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}