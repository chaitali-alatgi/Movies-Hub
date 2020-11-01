package com.example.movieshub.ui.adapter

import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView

abstract class ObservableRecyclerViewAdapter<T, Holder: RecyclerView.ViewHolder>(
    private val items: ObservableList<T>
): RecyclerView.Adapter<Holder>() {

    val ITEM = 0
    val LOADING = 1
    private var isLoadingAdded = false

    lateinit var onItemClickListener: (item: Any) -> Unit

    init{
        items.addOnListChangedCallback(object: ObservableList.OnListChangedCallback<ObservableList<T>>() {
            override fun onChanged(sender: ObservableList<T>?) {
                notifyDataSetChanged()
            }

            override fun onItemRangeMoved(
                sender: ObservableList<T>?,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(
                sender: ObservableList<T>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeInserted(
                sender: ObservableList<T>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(
                sender: ObservableList<T>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeChanged(positionStart, itemCount)
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(i: Int): T {
        return items[i]
    }

    fun getItems(): ObservableList<T> {
        return items
    }

    open fun add(t: T?) {
        items.add(t)
        notifyItemInserted(items.size - 1)
    }

    open fun addAll(mcList: List<T?>) {
        for (mc in mcList) {
            add(mc)
        }
    }

    open fun remove(city: T?) {
        val position: Int = items.indexOf(city)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    open fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    open fun isEmpty(): Boolean {
        return itemCount == 0
    }

}