package com.example.football.view.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.databinding.CustomCardViewBinding
import com.example.football.model.api.FootBallApiDetailsObject
import com.example.football.util.Util


class MatchesAdapter(private val onClickListener: OnClickListener) : ListAdapter<FootBallApiDetailsObject, MatchesAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: CustomCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FootBallApiDetailsObject,isHeader:Boolean) {
            binding.data = data
            binding.isHeader = isHeader
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FootBallApiDetailsObject>() {
        override fun areItemsTheSame(
            oldItem: FootBallApiDetailsObject,
            newItem: FootBallApiDetailsObject
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: FootBallApiDetailsObject,
            newItem: FootBallApiDetailsObject
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val withDataBinding: CustomCardViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.custom_card_view, parent, false)
          return  ViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (position - 1 == -1 || Util.getDateOnly(data.utcDate) !=  Util.getDateOnly(getItem(position-1).utcDate)){
            holder.bind(data,true)
        }else{
            holder.bind(data,false)
        }
    }

    class OnClickListener(val clickListener: (data: FootBallApiDetailsObject) -> Unit) {
        fun onClick(data: FootBallApiDetailsObject) = clickListener(data)
    }



}