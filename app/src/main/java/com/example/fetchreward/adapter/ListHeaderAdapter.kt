package com.example.fetchreward.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchreward.R
import com.example.fetchreward.modal.Item

class ListHeaderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<Any>()

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    fun setData(groupedItems: Map<Int, List<Item>>) {
        items.clear()
        groupedItems.forEach { (listId, itemsList) ->
            items.add(listId) // Add the header (listId)
            items.addAll(itemsList) // Add all items in this group
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Int -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
                ItemViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val listId = items[position] as Int
                holder.bind(listId)
            }
            is ItemViewHolder -> {
                val item = items[position] as Item
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvListId: TextView = itemView.findViewById(R.id.tvListId)

        fun bind(listId: Int) {
            tvListId.text = "List ID: $listId"
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        private val tvItemId: TextView = itemView.findViewById(R.id.tvItemId)

        fun bind(item: Item) {
            tvItemName.text = item.name
            tvItemId.text = "ID: ${item.id}"
        }
    }
}
