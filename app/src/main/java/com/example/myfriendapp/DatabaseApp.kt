package com.example.myfriendapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyFriend::class], version = 1)
abstract class DatabaseApp : RoomDatabase () {
    abstract fun myFriendDao(): MyFriendDao

    companion object {
        var INSTANCE: DatabaseApp? = null

        fun getDatabaseApp(context: Context): DatabaseApp? {
            if (INSTANCE == null) {
                synchronized(DatabaseApp::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseApp::class.java, "MyFriendAppDB"
                    ).build()
                }
            }
                return INSTANCE
            }
            fun destroyDatabase() {
                INSTANCE = null
            }
        }
    }