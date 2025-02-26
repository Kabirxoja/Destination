package com.example.destination.ui.home.vocabulary

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.destination.R
import com.example.destination.ui.home.vocabulary.ParentAdapter.DiffCallback

class ChildAdapter : ListAdapter<ChildItem, ChildAdapter.ChildViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_child_item_layout, parent, false)
        return ChildViewHolder(view, parent as RecyclerView)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val childItem = getItem(position)
        holder.bind(childItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<ChildItem>() {
        override fun areItemsTheSame(oldItem: ChildItem, newItem: ChildItem): Boolean {
            return oldItem.enExample == newItem.enExample // Or use a unique ID
        }

        override fun areContentsTheSame(oldItem: ChildItem, newItem: ChildItem): Boolean {
            return oldItem == newItem // Or implement a more specific check if needed
        }
    }

    inner class ChildViewHolder(itemView: View, private val recyclerView: RecyclerView) :
        RecyclerView.ViewHolder(itemView) {

        private val uzTextView: TextView = itemView.findViewById(R.id.uz_text_view)
        private val enTextView: TextView = itemView.findViewById(R.id.en_text_view)
        private val expandableButton: ImageView =
            itemView.findViewById(R.id.expandable_button_child)

        fun bind(childItem: ChildItem) {
            uzTextView.text = childItem.uzExample
            enTextView.text = childItem.enExample
            uzTextView.visibility = if (childItem.expandable) View.VISIBLE else View.GONE
            enTextView.visibility = if (!childItem.expandable) View.VISIBLE else View.GONE
            expandableButton.setImageResource(if (childItem.expandable) R.drawable.ic_reverse_front else R.drawable.ic_reverse_back) // Replace with your icons
        }

        init {
            expandableButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    (itemView.context as? AppCompatActivity)?.let { activity ->
                        val adapter = (recyclerView.adapter as? ChildAdapter)
                            ?: return@setOnClickListener  // Now recyclerView is available!
                        val childItem = adapter.getItem(position)
                        childItem.expandable = !childItem.expandable
                        adapter.notifyItemChanged(position)
                    }
                }
            }
        }
    }
}