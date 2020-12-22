package com.iml1s.interview.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.iml1s.interview.test.databinding.FragmentSearchBinding
import com.iml1s.interview.test.viewmodel.SearchViewModel
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber


class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater).apply {
        viewModel = this@SearchFragment.viewModel.apply {
            onChangeGrid.asFlow()
                .onEach { changeLayout(it) }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
        recyclerView.adapter = SearchResultAdapter(isGrid = false, this@SearchFragment.viewModel)
        lifecycleOwner = this@SearchFragment.viewLifecycleOwner
        searchBar.searchEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
                Timber.d("onSearchStateChanged: $enabled")
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                Timber.d("onSearchConfirmed: $text")
                val imm = getSystemService(requireContext(), InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(searchBar.windowToken, 0)
                searchBar.closeSearch()
            }

            override fun onButtonClicked(buttonCode: Int) {
                Timber.d("onButtonClicked: $buttonCode")
            }
        })
    }.root

    private fun FragmentSearchBinding.changeLayout(isGrid: Boolean) = recyclerView.run {
        layoutManager =
            if (isGrid) StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            else LinearLayoutManager(requireContext())

        adapter = recyclerView.adapter
    }

}
