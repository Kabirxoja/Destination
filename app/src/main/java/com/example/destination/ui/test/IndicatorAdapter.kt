package com.example.destination.ui.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.destination.databinding.RecycleIndicatorItemBinding

class IndicatorAdapter : RecyclerView.Adapter<IndicatorAdapter.MyViewHolder>() {

    private var list: MutableList<Int> = ArrayList()
    private var listener: OnClickItemListener?= null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IndicatorAdapter.MyViewHolder {
        val binding =
            RecycleIndicatorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndicatorAdapter.MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(private val binding: RecycleIndicatorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Int) {
            binding.unitText.text = "Unit"
            binding.numberText.text = item.toString()

            binding.root.setOnClickListener {
                listener?.onClickItem(item)
            }
        }
    }

    fun setOnClickListener(onClickItemListener: OnClickItemListener){
        listener = onClickItemListener
    }

    interface OnClickItemListener {
        fun onClickItem(item: Int)
    }

    fun updateList(newList: List<Int>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


}