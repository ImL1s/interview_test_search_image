package com.iml1s.interview.test.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iml1s.interview.test.BR
import com.iml1s.interview.test.databinding.ItemSearchBinding
import com.iml1s.interview.test.model.Hits
import com.iml1s.interview.test.viewmodel.SearchViewModel


class SearchResultAdapter(private val isGrid: Boolean, private val viewModel: SearchViewModel) :
    ListAdapter<Hits, SearchResultViewHolder>(diffBy { it.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            if (isGrid) ItemSearchBinding.inflate(LayoutInflater.from(parent.context))
            else ItemSearchBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }
}

class SearchResultViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(hits: Hits, viewModel: SearchViewModel) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.url, hits.largeImageUrl)
    }
}

class SingleFieldDiffUtils<T>(val fieldExtractor: (T) -> Any?) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) =
        fieldExtractor(oldItem) == fieldExtractor(newItem)

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem

}

typealias diffBy = SingleFieldDiffUtils<Hits>
