package blaze98.slalom.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import blaze98.slalom.R
import kotlinx.android.synthetic.main.history_item.view.*

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

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val curTodo = history[position]
        holder.itemView.apply {
            tvHistoryName.text = curTodo.name
            tvStatus.text = curTodo.status.toString()
            }
        }

    override fun getItemCount(): Int {
        return history.size
    }
}