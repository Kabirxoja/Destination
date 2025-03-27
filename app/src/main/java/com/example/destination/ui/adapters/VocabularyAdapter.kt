package com.example.destination.ui.adapters

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
import com.example.destination.data.data.VocabularyItem
import com.google.android.material.bottomsheet.BottomSheetDialog

class VocabularyAdapter : ListAdapter<VocabularyItem, VocabularyAdapter.ParentViewHolder>(
    DiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_parent_item_layout, parent, false)
        return ParentViewHolder(view, parent as RecyclerView)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentItem = getItem(position)
        holder.bind(parentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<VocabularyItem>() {
        override fun areItemsTheSame(oldItem: VocabularyItem, newItem: VocabularyItem): Boolean {
            return oldItem.enWord == newItem.enWord // Or use a unique ID
        }

        override fun areContentsTheSame(oldItem: VocabularyItem, newItem: VocabularyItem): Boolean {
            return oldItem == newItem // Or implement a more specific check if needed
        }
    }

    inner class ParentViewHolder(itemView: View, private val recyclerView: RecyclerView) :
        RecyclerView.ViewHolder(itemView) {

        private val enTextView: TextView = itemView.findViewById(R.id.en_text_view)
        private val uzTextView: TextView = itemView.findViewById(R.id.uz_text_view)
        private val audioSpeaker: ImageView = itemView.findViewById(R.id.audio_speaker)
        private val addNote: ImageView = itemView.findViewById(R.id.add_note)
        private val selectionType: TextView = itemView.findViewById(R.id.selection_type)


        fun bind(parentItem: VocabularyItem) {
            uzTextView.text = parentItem.uzWord
            enTextView.text = parentItem.enWord
            selectionType.text  = parentItem.type
        }

        private fun showBottomSheet(parentItem: VocabularyItem, itemView: View) {
            val bottomSheetDialog = BottomSheetDialog(itemView.context)
            val bottomSheetView = LayoutInflater.from(itemView.context).inflate(R.layout.bottom_sheet_vocab, null)

            bottomSheetView.findViewById<TextView>(R.id.en_word_bottom_sheet).text = parentItem.enExample
            bottomSheetView.findViewById<TextView>(R.id.uz_word_bottom_sheet).text = parentItem.uzExample
            bottomSheetView.findViewById<TextView>(R.id.defination_bottom_sheet).text =parentItem.definition

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }


        init {
            itemView.rootView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    (itemView.context as? AppCompatActivity)?.let { activity ->
                        val adapter = (recyclerView.adapter as? VocabularyAdapter)
                            ?: return@setOnClickListener  // Now recyclerView is available!
                        val parentItem = adapter.getItem(position)
                        showBottomSheet(parentItem, itemView)
                        adapter.notifyItemChanged(position)
                    }

                }
            }

            audioSpeaker.setOnClickListener{
                Toast.makeText(itemView.context.applicationContext, "audioSpeaker", Toast.LENGTH_SHORT).show()
            }

            addNote.setOnClickListener {
                Toast.makeText(itemView.context.applicationContext, "addNote", Toast.LENGTH_SHORT).show()

            }
        }
    }
}