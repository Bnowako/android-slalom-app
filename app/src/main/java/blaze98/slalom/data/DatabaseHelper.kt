package blaze98.slalom.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import blaze98.slalom.game.GameStatus

class DatabaseHelper(val context: Context): SQLiteOpenHelper(context,  "map-game-db",null, 1) {

    companion object {
        fun insertHistoryRecord(db: SQLiteDatabase, name: String, status: GameStatus) {
            val initialValue = ContentValues()
            initialValue.put("name", name)
            initialValue.put("status", status.toString())
            db.insert("HISTORY", null,initialValue)
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if( db != null) {
            val createTableSql =
                "CREATE TABLE HISTORY (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, STATUS TEXT)"
            db.execSQL(createTableSql)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }





}