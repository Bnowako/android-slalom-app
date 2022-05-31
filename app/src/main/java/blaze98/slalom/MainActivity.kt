package blaze98.slalom

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import blaze98.slalom.data.DatabaseHelper
import blaze98.slalom.history.HistoryAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = DatabaseHelper(this)
        val readable = dbHelper.readableDatabase
        val history = DatabaseHelper.getAllHistory(readable)

        btnStartGame.setOnClickListener {
            val intent = Intent(this, MapGame::class.java)
            showToastWithText("Game is starting..")
            startActivity(intent)
        }


        historyAdapter = HistoryAdapter(history)
        rvHistory.adapter = historyAdapter
        rvHistory.layoutManager = LinearLayoutManager(this)

    }


    private fun showToastWithText(text: String) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

}