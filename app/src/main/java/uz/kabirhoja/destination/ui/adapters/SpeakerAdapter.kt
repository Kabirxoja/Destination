package uz.kabirhoja.destination.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kabirhoja.destination.R
import uz.kabirhoja.destination.data.data.SpeakerItem
import com.kabirhoja.destination.databinding.RecycleSpeakerItemLayoutBinding
import uz.kabirhoja.destination.custom.AnimationButton.animateClick

class SpeakerAdapter : RecyclerView.Adapter<SpeakerAdapter.SpeakerViewHolder>() {
    private var listener: OnClickItemListener? = null
    private val list: MutableList<SpeakerItem> = ArrayList()
    private var selectedPosition: Int = RecyclerView.NO_POSITION


    inner class SpeakerViewHolder(private val binding: RecycleSpeakerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: SpeakerItem) {
            when (item.locale) {
                "en_US" -> {
                    binding.languageIcon.setImageResource(R.drawable.ic_flag_us)
                    binding.languageName.text = "American"
                }

                "en_GB" -> {
                    binding.languageIcon.setImageResource(R.drawable.ic_flag_uk)
                    binding.languageName.text = "British"
                }

                "en_AU" -> {
                    binding.languageIcon.setImageResource(R.drawable.ic_au)
                    binding.languageName.text = "Australian"
                }
            }

            binding.languageNumber.text = "Voice №" + (item.number+1).toString()


            if (adapterPosition == selectedPosition) {
                updateBackgrounds(binding.languageName)
            } else {
                resetBackgrounds(binding.languageName)
            }


            binding.root.setOnClickListener {
                val previousSelectedPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
                listener?.onClickItem(item)
                it.animateClick()

            }


        }


    }

    private fun updateBackgrounds(textView: TextView) {
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green))
    }

    private fun resetBackgrounds(textView: TextView) {
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.black))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerViewHolder {
        val binding = RecycleSpeakerItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpeakerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    fun setOnClickListener(onClickItemListener: OnClickItemListener) {
        listener = onClickItemListener
    }

    interface OnClickItemListener {
        fun onClickItem(item: SpeakerItem)
    }

    fun updateList(newList: List<SpeakerItem>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


}