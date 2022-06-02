package blaze98.slalom.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import blaze98.slalom.game.GameStatus
import blaze98.slalom.history.History
import java.time.LocalDateTime

class DatabaseHelper(val context: Context): SQLiteOpenHelper(context,  "map-game-db",null, 1) {

    companion object {
        fun insertHistoryRecord(db: SQLiteDatabase, name: String, status: GameStatus, date: LocalDateTime) {
            val initialValue = ContentValues()
            initialValue.put("name", name)
            initialValue.put("status", status.toString())
            initialValue.put("DATE_ADDED", date.toString())
            db.insert("HISTORY", null,initialValue)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getAllHistory(redeable: SQLiteDatabase): MutableList<History> {
            val cursor = redeable.query(
                "HISTORY", arrayOf("NAME", "STATUS", "DATE_ADDED"),
                null,
                null,
                null,
                null,
                null
            )
            val history = mutableListOf<History>()
            if (cursor.moveToFirst()) {
                do {
                    history.add(History(cursor.getString(0), GameStatus.valueOf(cursor.getString(1)), LocalDateTime.parse(cursor.getString(2))))
                } while (cursor.moveToNext())
            }
            return history
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if( db != null) {
            val createTableSql =
                "CREATE TABLE HISTORY (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, STATUS TEXT, DATE_ADDED DATE)"
            db.execSQL(createTableSql)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }





}