package uz.kabirhoja.destination.ui.adapters

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.kabirhoja.destination.R
import com.kabirhoja.destination.databinding.RecycleIndicatorItemBinding
import uz.kabirhoja.destination.custom.AnimationButton.animateClick
import uz.kabirhoja.destination.data.data.TestChoiceItem

class IndicatorAdapter : RecyclerView.Adapter<IndicatorAdapter.MyViewHolder>() {

    private var list: MutableList<TestChoiceItem> = ArrayList()
    private var listener: OnClickItemListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = RecycleIndicatorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(private val binding: RecycleIndicatorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: TestChoiceItem) {
            binding.unitText.text = "Unit"
            binding.numberText.text = item.unitNumber.toString()


            if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < list.size) {
                val isChecked = list[adapterPosition].checked

                val backgroundColor = if (isChecked) R.color.green else R.color.white
                val textColor = if (isChecked) R.color.white else R.color.black
                val color = ContextCompat.getColor(binding.root.context, backgroundColor)
                val colorStateList = ColorStateList.valueOf(color)

                ViewCompat.setBackgroundTintList(
                    binding.indicatorRecyclerView,
                    colorStateList
                )
                binding.unitText.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        textColor
                    )
                )
                binding.numberText.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        textColor
                    )
                )
            }


            binding.root.setOnClickListener {
                val selectedPosition = adapterPosition
                if (selectedPosition != RecyclerView.NO_POSITION) {
                    list[selectedPosition].checked = !item.checked
                    notifyItemChanged(selectedPosition)
                }
                listener?.onClickItem(item)
                Log.d("llll",item.toString())
                it.animateClick()
            }

        }


    }

    fun setOnClickListener(onClickItemListener: OnClickItemListener) {
        listener = onClickItemListener
    }

    interface OnClickItemListener {
        fun onClickItem(item: TestChoiceItem)
    }

    fun updateList(newList: List<TestChoiceItem>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }





}
