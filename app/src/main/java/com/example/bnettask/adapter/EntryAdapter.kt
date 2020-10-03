package com.example.bnettask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bnettask.R
import com.example.bnettask.data.Note
import kotlinx.android.synthetic.main.entry_view.view.*

class EntryAdapter(private val itemsList: ArrayList<Note>,
                   private val listener: ItemClickListener) :
RecyclerView.Adapter<EntryAdapter.ViewHolder>() {


    fun setNewData(newData: List<Note>) {
        itemsList.clear()
        itemsList.addAll(newData)
        notifyDataSetChanged()
    }

    fun addItem(item: Note) {
        itemsList.add(0, item)
        notifyItemInserted(0)
    }

    fun removeItem(item: Note) {
        itemsList.remove(item)
        notifyDataSetChanged()
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

        fun bind(note: Note) {
            entryId = note.id
            itemView.created_date.text = note.da
            itemView.updated_date.text = note.dm
            itemView.body.text = note.dm

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(adapterPosition)
        }
    }
}