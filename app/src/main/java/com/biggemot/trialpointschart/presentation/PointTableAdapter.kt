package com.biggemot.trialpointschart.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.biggemot.trialpointschart.databinding.ItemPointBinding
import com.biggemot.trialpointschart.presentation.model.PointModel

class PointTableAdapter : ListAdapter<PointModel, PointTableAdapter.Holder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PointModel>() {
            override fun areItemsTheSame(oldItem: PointModel, newItem: PointModel): Boolean {
                return oldItem.x == newItem.x
            }

            override fun areContentsTheSame(oldItem: PointModel, newItem: PointModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemPointBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(private val binding: ItemPointBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(point: PointModel) {
            with(binding) {
                xTextView.text = point.x.toString()
                yTextView.text = point.y.toString()
            }
        }
    }
}