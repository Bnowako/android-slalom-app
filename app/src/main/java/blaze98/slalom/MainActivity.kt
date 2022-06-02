package blaze98.slalom

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import blaze98.slalom.data.DatabaseHelper
import blaze98.slalom.history.HistoryAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.history_item.*


class MainActivity : AppCompatActivity() {

    private lateinit var historyAdapter: HistoryAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRvView()
        btnStartGame.setOnClickListener {
            val intent = Intent(this, MapGame::class.java)
            showToastWithText("Game is starting..")
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createRvView() {
        val dbHelper = DatabaseHelper(this)
        val readable = dbHelper.readableDatabase
        val history = DatabaseHelper.getAllHistory(readable)
        historyAdapter = HistoryAdapter(history)
        rvHistory.adapter = historyAdapter
        rvHistory.layoutManager = LinearLayoutManager(this)
    }


    private fun showToastWithText(text: String) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        createRvView()
    }

}