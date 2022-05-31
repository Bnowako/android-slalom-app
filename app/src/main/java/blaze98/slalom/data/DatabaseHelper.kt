package blaze98.slalom.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import blaze98.slalom.game.GameStatus
import blaze98.slalom.history.History

class DatabaseHelper(val context: Context): SQLiteOpenHelper(context,  "map-game-db",null, 1) {

    companion object {
        fun insertHistoryRecord(db: SQLiteDatabase, name: String, status: GameStatus) {
            val initialValue = ContentValues()
            initialValue.put("name", name)
            initialValue.put("status", status.toString())
            db.insert("HISTORY", null,initialValue)
        }

        fun getAllHistory(redeable: SQLiteDatabase): MutableList<History> {
            val cursor = redeable.query(
                "HISTORY", arrayOf("NAME", "STATUS"),
                null,
                null,
                null,
                null,
                null
            )
            val history = mutableListOf<History>()
            if (cursor.moveToFirst()) {
                do {
                    history.add(History(cursor.getString(0), GameStatus.valueOf(cursor.getString(1))))
                } while (cursor.moveToNext())
            }
            return history
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