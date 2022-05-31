package blaze98.slalom.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(val context: Context): SQLiteOpenHelper(context,  "map-game-db",null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        if( db != null) {
            val createTableSql =
                "CREATE TABLE HISTORY (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, STATUS TEXT)"
            db.execSQL(createTableSql)
            insertOne(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    private fun insertOne(db: SQLiteDatabase) {
        val initialValue = ContentValues()
        initialValue.put("name", "new")
        initialValue.put("status", "won")
        db.insert("HISTORY", null,initialValue)
    }




}