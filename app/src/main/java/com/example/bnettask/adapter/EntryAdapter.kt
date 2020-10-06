package com.example.bnettask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bnettask.R
import com.example.bnettask.data.Entry
import com.example.bnettask.date.DateParser
import kotlinx.android.synthetic.main.entry_view.view.*
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

class EntryAdapter(private val itemsList: ArrayList<Entry>,
                   private val listener: ItemClickListener) :
RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    fun setNewData(newData: List<Entry>) {
        itemsList.clear()
        itemsList.addAll(newData)
        notifyDataSetChanged()
    }

    fun addItem(item: Entry) {
        itemsList.add(0, item)
        notifyItemInserted(0)
    }

    fun removeItem(item: Entry) {
        itemsList.remove(item)
        notifyDataSetChanged()
    }

    fun getEntryList(): List<Entry> {
        return itemsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.entry_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var entryId: String? = null

        fun bind(entry: Entry) {
            entryId = entry.id
            itemView.created_date.text = DateParser.parse(entry.da)
            itemView.updated_date.text = DateParser.parse(entry.dm)
            if (entry.da == entry.dm) {
                itemView.updated_date.visibility = View.GONE
                itemView.updated.visibility = View.GONE
            }
            itemView.body.text = entry.body

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(adapterPosition)
        }
    }
}