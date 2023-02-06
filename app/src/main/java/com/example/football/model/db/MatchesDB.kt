package com.example.football.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MatchesDBObject::class], version = 1)
@TypeConverters(Converters::class)
abstract class MatchesDB : RoomDatabase() {
    abstract val matchDao: MatchesDao
    companion object {
        @Volatile
        private var INSTANCE: MatchesDB? = null
        fun getInstance(context: Context): MatchesDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MatchesDB::class.java,
                        "Data_Base"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}