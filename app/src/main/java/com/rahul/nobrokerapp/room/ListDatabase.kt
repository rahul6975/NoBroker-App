package com.rahul.nobrokerapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ListEntity::class], version = 1)
abstract class ListDatabase : RoomDatabase() {

    abstract fun getListDao(): ListDao

    companion object {
        private var INSTANCE: ListDatabase? = null

        fun getDatabase(context: Context): ListDatabase {
            if (INSTANCE == null) {
                val builder = Room.databaseBuilder(
                    context,
                    ListDatabase::class.java,
                    "list_db"
                )
                INSTANCE = builder.build()
                return INSTANCE!!
            }
            return INSTANCE!!
        }

    }

}