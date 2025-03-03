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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.destination.R

class ParentAdapter : ListAdapter<ParentItem, ParentAdapter.ParentViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_parent_item_layout, parent, false)
        return ParentViewHolder(view, parent as RecyclerView)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentItem = getItem(position)
        holder.bind(parentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<ParentItem>() {
        override fun areItemsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
            return oldItem.enWord == newItem.enWord // Or use a unique ID
        }

        override fun areContentsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
            return oldItem == newItem // Or implement a more specific check if needed
        }
    }

    inner class ParentViewHolder(itemView: View, private val recyclerView: RecyclerView) :
        RecyclerView.ViewHolder(itemView) {

        private val enTextView: TextView = itemView.findViewById(R.id.en_text_view)
        private val uzTextView: TextView = itemView.findViewById(R.id.uz_text_view)
        private val definitionTextView: TextView = itemView.findViewById(R.id.definition_text_view)
        private val audioSpeaker: ImageView = itemView.findViewById(R.id.audio_speaker)

//        private val expandableButton: ImageView = itemView.findViewById(R.id.expandable_button_parent)

        private val childRecyclerView: RecyclerView = itemView.findViewById(R.id.child_recycler_view)

        fun bind(parentItem: ParentItem) {
            uzTextView.text = parentItem.uzWord
            enTextView.text = parentItem.enWord
            definitionTextView.text = parentItem.definition
//            expandableButton.setImageResource(if (parentItem.isExpanded) R.drawable.ic_cursor_up else R.drawable.ic_cursor_down) // Replace with your icons
            childRecyclerView.visibility = if (parentItem.isExpanded) View.VISIBLE else View.GONE

            if (parentItem.isExpanded) {
                val childAdapter = ChildAdapter()
                childRecyclerView.adapter = childAdapter
                childAdapter.submitList(parentItem.children) // Or use a separate list if needed.
                childRecyclerView.layoutManager =
                    LinearLayoutManager(childRecyclerView.context) // Set a LayoutManager
            }
        }

        init {
            itemView.rootView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    (itemView.context as? AppCompatActivity)?.let { activity ->
                        val adapter = (recyclerView.adapter as? ParentAdapter)
                            ?: return@setOnClickListener  // Now recyclerView is available!
                        val parentItem = adapter.getItem(position)
                        parentItem.isExpanded = !parentItem.isExpanded
                        adapter.notifyItemChanged(position)
                    }
                }
            }

            audioSpeaker.setOnClickListener{
                Log.d("sadsad","audioSpeaker")
            }
        }
    }
}