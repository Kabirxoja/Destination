package com.example.destination.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.destination.data.data.HomeItem
import com.example.destination.databinding.RecycleThemeItemLayoutBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var list: MutableList<HomeItem> = ArrayList()
    private var listener: OnClickItemListener? = null

    inner class MyViewHolder(private val binding: RecycleThemeItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: HomeItem) {
            binding.unitNumber.text = item.unitNumber.toString()
            binding.unitName.text = item.unitName

            // Set click listener
            binding.root.setOnClickListener {
                listener?.onClickItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecycleThemeItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickItemListener(onClickItemListener: OnClickItemListener) {
        listener = onClickItemListener
    }

    interface OnClickItemListener {
        fun onClickItem(item: HomeItem)
    }

    fun updateList(newList: List<HomeItem>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}