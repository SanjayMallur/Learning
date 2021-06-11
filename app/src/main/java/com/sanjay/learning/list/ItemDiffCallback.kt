package com.sanjay.learning.list

import androidx.recyclerview.widget.DiffUtil
import com.sanjay.learning.data.Pokemon

object ItemDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem == newItem
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem.name == newItem.name
}
