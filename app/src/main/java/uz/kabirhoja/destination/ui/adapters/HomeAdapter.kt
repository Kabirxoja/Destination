package uz.kabirhoja.destination.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kabirhoja.destination.databinding.RecycleThemeItemLayoutBinding
import uz.kabirhoja.destination.custom.AnimationButton.animateClick
import uz.kabirhoja.destination.data.data.HomeItem

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var list: MutableList<HomeItem> = ArrayList()
    private var listener: OnClickItemListener? = null

    inner class MyViewHolder(private val binding: RecycleThemeItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: HomeItem) {
            binding.unitNumber.text = item.unitNumber.toString()
            binding.unitName.text = item.unitName

            binding.root.setOnClickListener {
                listener?.onClickItem(item)
                it.animateClick()
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