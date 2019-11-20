package com.example.wiprointerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wiprointerview.R
import com.example.wiprointerview.databinding.ItemBinding

import com.example.wiprointerview.model.Item


class ItemAdapter(var list: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var itemBinding: ItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent?.context),
            R.layout.item,
            parent,
            false
        )
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        list?.get(position).let {
            if (it.description == null && it.imageHref == null && it.title == null) {
                holder.itemBinding.cardvisibility = true
                holder.itemBinding.root.visibility = View.GONE
                holder.itemBinding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
            } else {
                   holder.itemBinding.cardvisibility = false

            }
            holder.itemBinding.item = it
        }
    }

    override fun getItemCount(): Int {
        return list?.size
    }


    inner class ItemViewHolder(var itemBinding: ItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }

    fun update(items: List<Item>) {
        this.list = items
        notifyDataSetChanged()
    }

}