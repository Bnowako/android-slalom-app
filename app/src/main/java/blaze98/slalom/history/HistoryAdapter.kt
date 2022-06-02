package blaze98.slalom.history

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import blaze98.slalom.R
import blaze98.slalom.game.GameStatus
import kotlinx.android.synthetic.main.history_item.view.*
import java.time.format.DateTimeFormatter

class HistoryAdapter(private val history: MutableList<History>):RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.history_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val curHistoryRecord = history[position]
        holder.itemView.apply {
            tvHistoryName.text = curHistoryRecord.name
            tvStatus.text = if (curHistoryRecord.status == GameStatus.USER_WON) "Won :)" else "Lost :("
            tvDate.text = curHistoryRecord.date.format(DateTimeFormatter.ISO_LOCAL_TIME).toString()
            }
        }

    override fun getItemCount(): Int {
        return history.size
    }
}